# Arquitectura — LosJardines KMP

Aplicación **Kotlin Multiplatform** (Android · iOS · Desktop/JVM) con UI en **Compose Multiplatform**, siguiendo **Clean Architecture** modular y patrón **MVI** en presentación.

## Stack

| Capa | Tecnología |
|------|-----------|
| UI | Compose Multiplatform · Compose Navigation |
| Estado | StateFlow · MVI (State / Event / Effect) |
| DI | Koin 4 (`includes()` por capa) |
| Backend | Firebase Auth + Firestore (GitLive) |
| BD local | SQLDelight (driver por plataforma) |
| Async | Coroutines |
| Errores | `Either<Failure, T>` (manejo funcional) |
| Reportes | Apache POI (Excel) |
| Config | BuildKonfig |

## Módulos y flujo de dependencias

Las dependencias apuntan **siempre hacia el núcleo** (unidireccional).

```
PRESENTATION   :composeApp ── feature:ui
                   │              │
DOMAIN             │           :domain  (UseCases · DTOs · interfaces Repository)
                   │              │
DATA               │         feature:data  (Impl · Mappers)
                   │              │
CORE        core-common · core-ui · core-network · core-database
```

Dependencias de proyecto por módulo:

| Módulo | Depende de |
|--------|-----------|
| `:composeApp` | `feature:ui`, `feature:data`, `:domain`, `core-ui`, `core-common` |
| `feature:ui` | `core-ui`, `core-common`, `:domain` |
| `feature:data` | `core-common`, `core-network`, `core-database`, `:domain` |
| `:domain` | `core-common` |
| `core-network` | `core-common` |
| `core-ui` | `core-common` |
| `core-database` | — |
| `core-common` | — |

## Capas

### Presentation
- **`:composeApp`** — composition root. `App`, `NavManager`, `NavigationViewModel`, `initKoinModularization`. Lógica específica de plataforma vía `expect/actual` (Android `MyApp`, iOS `MainViewController`, Desktop `main.kt`).
- **`feature:ui`** — ViewModels MVI (`Login`, `Registration`, `Home`, `Room`, `Consultation`) sobre `BaseViewModel<State, Event, Effect>`, pantallas Compose y contratos.

### Domain (`:domain`)
- **UseCases** orquestan la lógica de negocio y devuelven/consumen `Either<Failure, T>`.
- **DTOs** (`RegistrationDto`, `RoomDto`, catálogos…).
- **Interfaces de Repository** expresadas por **intención**, sin filtrar detalles de persistencia:
  - `FirestoreRepository`: `sendClient`, `deleteClient`, `updateClientField`, `updateRoomState`, `getRoomState`.
  - `AuthRepository`, `DatabaseRepository`.
- El dominio **no conoce** nombres de colecciones/campos de Firestore. `FielTypeRegister` es un enum semántico puro.

### Data (`feature:data`)
- Implementaciones de los repositorios.
- **Mappers** `toData()` / `toDomain()` entre modelos de red/BD y DTOs de dominio.
  - `FielTypeRegisterMapper.toFirestoreField()` traduce el enum de dominio al nombre de campo de Firestore (la única capa que conoce esa correspondencia, junto a `core-network`).

### Core
- **`core-common`** — `BaseViewModel`, `BaseClient`, `Either`, `Failure` (sealed), `NetworkChecker` (expect), `StateProcess`, utilidades, `ExcelEditor`.
- **`core-ui`** — `AppTheme`, componentes reutilizables (`ButtonAJ`, `TextInputAJ`, `DropDownAJ`, `DatePicker` por plataforma, diálogos…).
- **`core-network`** — Firebase Auth/Firestore (GitLive), `LoginManager/Service`, `FirestoreManager/Service`, modelos de red y `FirestoreConstance` (nombres de colecciones/campos).
- **`core-database`** — SQLDelight, `DatabaseManager`, y el `SqlDriver` provisto por plataforma vía Koin.

## Manejo de errores — `Either<Failure, T>`

`BaseClient` hace un pre-check de conectividad y envuelve las llamadas en `try/catch`, devolviendo:

`FirebaseAuthFailure` · `FirestoreFailure` · `DatabaseFailure` · `MapperToDomain` · `InternetConnection` · `UnknownFailure`

## Estrategia offline-first (en los UseCases)

La decisión SYNC/PENDING se toma según el **resultado** de la operación, no por un chequeo previo de red:

- `Either.Success` → guarda local con `StateProcess.SYNC`.
- `Either.Error` con `Failure.InternetConnection` → guarda como `PENDING_INSERT` / `PENDING_UPDATE` (se reintenta luego).
- Cualquier otro `Failure` → se propaga (no se encola una operación que nunca va a sincronizar).

## Inyección de dependencias — cadena `includes()`

`composeApp` solo carga la cima de la cadena; cada capa arrastra sus propios sub-módulos mediante `includes()`:

```kotlin
// composeApp/di/ShareModule.kt
modules(uiModules, dataModules, splashScreenModules, networkUtilsModule)

// feature:ui/di/UiModules.kt
val uiModules = module { includes(domainModules); /* viewModels */ }

// feature:data/di/DataModules.kt
val dataModules = module { includes(firebaseModules, databaseModules); /* repos */ }

// core-database/di/DatabaseModules.kt
val databaseModules = module { includes(platformDatabaseModule); /* Database, DatabaseManager */ }
```

Resumen del grafo de módulos Koin:

```
initKoinModularization
 ├── uiModules ──includes──▶ domainModules
 ├── dataModules ──includes──▶ firebaseModules + databaseModules ──includes──▶ platformDatabaseModule
 ├── splashScreenModules        (NavigationViewModel)
 └── networkUtilsModule         (expect/actual: NetworkChecker por plataforma)
```

## Multiplataforma — `expect/actual`

- `NetworkChecker` (Android/iOS/Desktop).
- `ExcelEditor` / `ExcelTemplateProvider`.
- `DatePicker`.
- `platformDatabaseModule` — provee el `SqlDriver` de SQLDelight por plataforma:
  - **Android** → `AndroidSqliteDriver(..., androidContext(), ...)` (el `Context` se resuelve desde Koin, sin estado global).
  - **iOS** → `NativeSqliteDriver`.
  - **Desktop** → `JdbcSqliteDriver`.

## Convenciones

- Las clases base viven en `core-common` (`base/ui`, `base/either`, `base/error`, `base/network`).
- Los `val xxxModules` de Koin viven en el paquete `com.pe.losjardines.di` de cada módulo Gradle.
- Cada feature de presentación tiene su tripleta de contrato MVI: `*State` · `*Event` · `*Effect`.
- Los detalles de persistencia (nombres de colecciones/campos de Firestore, drivers de BD) **no suben** del nivel `core-network` / `feature:data`.
