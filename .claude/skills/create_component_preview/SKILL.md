---
name: create_component_preview
description: Genera funciones @Preview para un componente Compose del proyecto Los Jardines, mapeando los distintos estados/variantes que puede mostrar (vacío, con datos, seleccionado, deshabilitado, cada valor de un enum, etc.), siguiendo el patrón de preview de `ReservationMobileScreen`. Úsala cuando el usuario pida "crear preview", "preview de un componente", "previews de estados", "agrega @Preview" o algo equivalente. Pregunta primero por el archivo del componente si no lo indicó.
---

# Crear previews de un componente (mapeando sus estados)

Esta skill genera funciones `@Preview` para un componente existente, cubriendo **cada estado o
variante visual** que el componente puede mostrar. La referencia canónica del patrón de preview es
`feature/ui/src/commonMain/kotlin/com/pe/losjardines/presentation/content/reservation/screen/ReservationMobileScreen.kt`
(ver `ReservationMobileScreenPreview`).

## 1. Pedir la información al usuario

Antes de escribir nada, asegúrate de tener:

1. **El archivo del componente** al que se le agregarán los previews (ruta o nombre). Si no lo
   indicó, pregúntalo con `AskUserQuestion`.

No inventes componentes: la skill agrega previews a algo que **ya existe**.

## 2. Leer el componente y el patrón de referencia

Siempre lee, por si la base cambió:

- El **archivo del componente** objetivo (para conocer su firma, parámetros y de qué depende su UI).
- `.../reservation/screen/ReservationMobileScreen.kt` → función `ReservationMobileScreenPreview`
  (patrón para pantallas con `State`).
- Si el componente es una **hoja reutilizable** (card, chip, fila, ítem), revisa un preview de
  componente hoja existente, p. ej. `RoomCard` / `RoomCardPreview`, o
  `core/core-ui/.../components/chip/ChipAJ.kt`.

## 3. Identificar el tipo de componente

Determina cuál de los dos casos aplica:

- **Pantalla con estado (MVI):** recibe un `<Feature>State` (u objeto de estado) y un
  `dispatcherEvent`. En estos casos **se previsualiza el composable sin estado** (normalmente el
  `private fun <Feature>Content(...)`), **NUNCA** el composable público que usa `koinViewModel()`
  (un preview no tiene contenedor de Koin).
- **Componente hoja:** recibe parámetros directos (booleanos, enums, textos, íconos nullable,
  objetos de estilo). Se previsualiza el componente **directamente** con cada variante.

## 4. Mapear los estados / variantes (paso clave)

Enumera los estados **que el composable previsualizado realmente refleja visualmente** y crea un
preview por cada uno. Ojo: si un estado (p. ej. `loading` o `errorMessage`) se maneja en el
composable externo con `koinViewModel()` y NO en el `Content`, ese estado **no** se ve en el preview
del `Content` → no lo mapees ahí.

Ejes típicos a cubrir:

- **Colecciones:** vacío (muestra el empty state) **y** con datos de muestra.
- **Booleanos:** cada valor relevante (`isSelected` true/false, `enabled` true/false,
  `showClearFilter`, `isExpanded`, …).
- **Enums / estados de dominio:** un preview por valor significativo (p. ej. para reservas:
  `Check-in`, `Occupied`, `Canceled`).
- **Nullable opcionales:** con y sin (p. ej. `leadingIcon = null` vs con ícono).
- **Estado de error/mensaje:** solo si el composable previsualizado lo pinta.

Usa datos de muestra realistas (nombres, fechas, montos) para las colecciones.

## 5. Plantillas

### Caso A — Pantalla con `State` (previsualiza el `Content` sin estado)

```kotlin
@Preview
@Composable
private fun <Feature>Content<EstadoLabel>Preview() {
    AppTheme {
        Box(modifier = Modifier.background(Color.White)) {
            <Feature>Content(
                title = "<Feature>",
                dispatcherEvent = {},
                uiState = <Feature>State(
                    // TODO: setear los campos que representan este estado
                ),
                // pasa objetos anidados por defecto si el Content los requiere, p. ej.:
                // catalog = <Feature>ViewModel.CatalogState()
            )
        }
    }
}
```

Genera **un `@Preview` por estado mapeado** en el paso 4. Ejemplo para una lista:
`<Feature>ContentEmptyPreview` (lista vacía) y `<Feature>ContentWithDataPreview`
(`uiState = <Feature>State(items = listOf(/* muestras */))`).

### Caso B — Componente hoja (previsualiza el componente directo)

```kotlin
@Preview
@Composable
private fun <Component><VarianteLabel>Preview() {
    AppTheme {
        Box(modifier = Modifier.background(Color.White).padding(16.dp)) {
            <Component>(
                // parámetros de esta variante concreta
            )
        }
    }
}
```

Ejemplo (para un chip con `isSelected` y `enabled`): `ChipAJNormalPreview`,
`ChipAJSelectedPreview`, `ChipAJDisabledPreview`, `ChipAJWithIconPreview`.

> Si conviene ver varias variantes juntas, es válido un solo `@Preview` con una `Column`/`Row`
> que apile las variantes, además (o en lugar) de los previews individuales.

## 6. Reglas

- **Import de Preview multiplataforma:** siempre
  `import org.jetbrains.compose.ui.tooling.preview.Preview`, **NO** `androidx.compose.ui.tooling.preview.Preview`.
- Los previews van **`private`** (salvo razón para exponerlos) y se agregan **al final del mismo
  archivo** del componente.
- **Siempre** envuelve en `AppTheme { Box(modifier = Modifier.background(Color.White)) { ... } }`
  (`com.pe.losjardines.values.AppTheme`, `androidx.compose.ui.graphics.Color`). Para componentes
  hoja, añade `.padding(16.dp)` para que no queden pegados al borde.
- En pantallas MVI **nunca** invoques el composable con `koinViewModel()` dentro del preview:
  usa el `Content` sin estado y pásale un `<Feature>State(...)` de muestra y un `dispatcherEvent = {}`.
- Nombra cada preview de forma descriptiva por su estado:
  `<Nombre><EstadoLabel>Preview` (p. ej. `...EmptyPreview`, `...WithDataPreview`,
  `...SelectedPreview`, `...DisabledPreview`, `...CheckInPreview`).
- No modifiques la lógica del componente; solo agrega los `@Preview`. Si ya existe un preview,
  complétalo/añade los que falten en vez de duplicar.
- Agrega solo los imports que falten (`Preview`, `AppTheme`, `Box`, `Modifier`, `background`,
  `Color`, `padding`, y los tipos de estado/muestra que uses).
