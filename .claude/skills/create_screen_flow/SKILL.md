---
name: create_screen_flow
description: Genera una sección/feature nueva de UI con la arquitectura MVI del proyecto Los Jardines (contract State/Event/Effect, ViewModel y MobileScreen con preview), replicando el patrón de `presentation/content/consultation`. Úsala cuando el usuario pida "crear una sección", "nueva feature de contenido", "plantilla MVI" o algo equivalente. Pregunta primero la ubicación del package y el nombre de la sección.
---

# Crear una sección de contenido (plantilla MVI)

Esta skill crea los archivos base de una sección de UI siguiendo la arquitectura MVI usada en
el proyecto. La referencia canónica es `feature/ui/src/commonMain/kotlin/com/pe/losjardines/presentation/content/consultation`.

## 1. Pedir la información al usuario

Antes de crear nada, pregunta (usa `AskUserQuestion` si conviene):

1. **Ubicación del package** donde se creará la sección. Por defecto es
   `feature/ui/src/commonMain/kotlin/com/pe/losjardines/presentation/content/`.
   El usuario indica el nombre del sub-package, p. ej. `reservation`, `payment`, `report`.
2. **Nombre de la sección** (en PascalCase, p. ej. `Reservation`). Si el usuario solo da el
   package en minúscula, deriva el PascalCase automáticamente (`reservation` → `Reservation`).

Deriva estas variables:
- `<feature>` = nombre del sub-package en minúscula (ej. `reservation`).
- `<Feature>` = nombre en PascalCase (ej. `Reservation`).
- `<basePackage>` = package completo, ej. `com.pe.losjardines.presentation.content.reservation`.

## 2. Verificar el patrón antes de escribir

Lee estos archivos de `consultation` (o `room`, que es más simple) para copiar el estilo exacto,
por si la base cambió desde que se escribió esta skill:

- `.../content/consultation/contract/ConsultationState.kt`
- `.../content/consultation/contract/ConsultationEvent.kt`
- `.../content/consultation/contract/ConsultationEffect.kt`
- `.../content/consultation/viewmodel/ConsultationViewModel.kt`
- `.../content/consultation/screen/ConsultationMobileScreen.kt`

Clases base relevantes (en `core/core-common`):
`BaseViewModel<S: BaseUiState, E: BaseEvent, F: BaseEffect>`, con helpers
`updateState { copy(...) }`, `executeTask(task, onSuccess, onError)`, `sendEffect(...)`.

## 3. Archivos a crear

Crea exactamente estos 5 archivos dentro del package indicado:

### `contract/<Feature>State.kt`
```kotlin
package <basePackage>.contract

import com.pe.losjardines.base.ui.BaseUiState

data class <Feature>State(
    val loading: Boolean = false,
    val errorMessage: String? = null
): BaseUiState
```

### `contract/<Feature>Event.kt`
```kotlin
package <basePackage>.contract

import com.pe.losjardines.base.ui.BaseEvent

sealed interface <Feature>Event: BaseEvent {
    data object Load: <Feature>Event
}
```

### `contract/<Feature>Effect.kt`
```kotlin
package <basePackage>.contract

import com.pe.losjardines.base.ui.BaseEffect

sealed interface <Feature>Effect: BaseEffect {
}
```

### `viewmodel/<Feature>ViewModel.kt`
```kotlin
package <basePackage>.viewmodel

import com.pe.losjardines.base.error.Failure
import com.pe.losjardines.base.error.getMessage
import com.pe.losjardines.base.ui.BaseViewModel
import <basePackage>.contract.<Feature>Effect
import <basePackage>.contract.<Feature>Event
import <basePackage>.contract.<Feature>State

class <Feature>ViewModel(
    // TODO: inyectar los use cases necesarios
): BaseViewModel<<Feature>State, <Feature>Event, <Feature>Effect>(<Feature>State()) {

    override fun onEvent(event: <Feature>Event) {
        when(event){
            is <Feature>Event.Load -> load()
        }
    }

    private fun load() {
        updateState { copy(loading = true) }
        // TODO: reemplazar por la llamada real al use case con executeTask(...).
        updateState { copy(loading = false) }
    }

    private fun handleError(failure: Failure){
        updateState { copy(loading = false, errorMessage = failure.getMessage()) }
    }

    fun hideErrorMessage(){
        updateState { copy(errorMessage = null) }
    }
}
```

### `screen/<Feature>MobileScreen.kt`
```kotlin
package <basePackage>.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LifecycleEventEffect
import com.pe.losjardines.components.dialog.ResultDialog
import com.pe.losjardines.components.loading.LoadingAJ
import com.pe.losjardines.presentation.components.HeaderComponent
import <basePackage>.contract.<Feature>Event
import <basePackage>.contract.<Feature>State
import <basePackage>.viewmodel.<Feature>ViewModel
import com.pe.losjardines.values.AppTheme
import com.pe.losjardines.values.AppTypography
import com.pe.losjardines.values.LocalAppTypographyCore
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun <Feature>MobileScreen(
    title: String,
    viewModel: <Feature>ViewModel = koinViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    LifecycleEventEffect(Lifecycle.Event.ON_RESUME){
        viewModel.onEvent(<Feature>Event.Load)
    }

    if(uiState.loading){
        LoadingAJ(
            title = "<Feature>",
            subtitle = "Obteniendo información"
        )
    }

    ResultDialog(
        modifier = Modifier,
        title = "Sucedió un inconveniente",
        description = uiState.errorMessage,
        isSuccess = false,
        visibility = uiState.errorMessage != null,
        onDismiss = { viewModel.hideErrorMessage() }
    )

    <Feature>Content(
        dispatcherEvent = viewModel::onEvent,
        title = title,
        uiState = uiState
    )
}

@Composable
private fun <Feature>Content(
    dispatcherEvent: (<Feature>Event) -> Unit,
    title: String,
    uiState: <Feature>State,
    typography: AppTypography = LocalAppTypographyCore.current
) {
    Column(
        modifier = Modifier.fillMaxSize().padding(horizontal = 16.dp)
    ) {
        HeaderComponent(
            modifier = Modifier.fillMaxWidth().height(48.dp),
            title = title
        )

        Spacer(modifier = Modifier.size(16.dp))

        // TODO: construir la UI usando `uiState` y emitiendo eventos con `dispatcherEvent`.
    }
}

@Preview
@Composable
private fun <Feature>MobileScreenPreview() {
    AppTheme {
        Box(modifier = Modifier.background(Color.White)){
            <Feature>Content(
                title = "<Feature>",
                dispatcherEvent = {},
                uiState = <Feature>State()
            )
        }
    }
}
```

## 4. Registrar en Koin (obligatorio)

Sin esto, `koinViewModel()` falla en runtime. Edita
`feature/ui/src/commonMain/kotlin/com/pe/losjardines/di/UiModules.kt`:

- Añade el import `import <basePackage>.viewmodel.<Feature>ViewModel`.
- Añade `viewModelOf(::<Feature>ViewModel)` junto a los demás.

## 5. Pasos que NO hace la skill (menciónalos al usuario)

- **Navegación**: para mostrar la pantalla hay que llamar `<Feature>MobileScreen(...)` dentro del
  `composable(...)` correspondiente en
  `composeApp/.../navigation_content/navContentMobileManager.kt`. La ruta suele existir ya en
  `ItemsContentNavScreen`.
- **Use cases / repositorio**: la plantilla deja `load()` con un TODO. Hay que crear los use cases
  en `domain` y registrarlos en `DomainModules` para inyectarlos en el ViewModel.

## 6. Preview del componente (obligatorio)

**Todo componente que generes debe llevar su `@Preview`.** Es una convención del proyecto
(ver `ConsultationMobileScreenPreview` y `RoomCardPreview`). Reglas:

- La pantalla generada **siempre** incluye un `@Preview private fun <Feature>MobileScreenPreview()`
  que renderiza el `<Feature>Content` (NO el composable con `koinViewModel()`, porque un preview no
  tiene contenedor de Koin) envuelto en `AppTheme { Box(background = Color.White) { ... } }` y
  usando un `<Feature>State()` de ejemplo.
- Si además creas **sub-componentes reutilizables** (cards, filas de tabla, secciones, ítems de
  lista, etc.), cada uno debe tener su propio `@Preview` con datos de muestra, igual que
  `RoomCardPreview` para `RoomCard`.
- Usa siempre `import org.jetbrains.compose.ui.tooling.preview.Preview` (multiplataforma), NO el
  `androidx.compose.ui.tooling.preview.Preview`.
- Los preview van marcados `private` salvo que exista una razón para exponerlos.

## 7. Reglas

- Solo crea la plantilla mínima; no inventes lógica de negocio ni campos de estado que no existan.
- Respeta el nombre de packages y la convención `<Feature>State/Event/Effect/ViewModel/MobileScreen`.
- No dupliques una sección que ya exista: si el package ya tiene archivos, pregunta antes de sobrescribir.
