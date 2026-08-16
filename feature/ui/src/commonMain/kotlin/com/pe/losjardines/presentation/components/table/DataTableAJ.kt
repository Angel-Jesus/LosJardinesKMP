package com.pe.losjardines.presentation.components.table

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.pe.losjardines.components.dialog.TypeField
import com.pe.losjardines.presentation.content.utils.FieldRegistration
import com.pe.losjardines.values.AppTypography
import com.pe.losjardines.values.BackgroundOverlay
import com.pe.losjardines.values.BackgroundTableBrand
import com.pe.losjardines.values.DefaultTextColor
import com.pe.losjardines.values.DividerGrayColor
import com.pe.losjardines.values.LocalAppTypographyCore
import com.pe.losjardines.values.SoftTextColor

/**
 * Parámetros del campo que se está editando desde una celda de la tabla.
 * Compartido por las tablas de consulta (registros) y de reservas.
 */
data class UpdateFieldParams(
    val show: Boolean = false,
    val id: Long = 0L,
    val collection: String = "",
    val idNetwork: String = "",
    val field: FieldRegistration? = null,
    val title: String = "",
    val description: String = "",
    val value: String = "",
    val typeField: TypeField = TypeField.TEXT,
    val listOption: List<String> = emptyList()
)

/**
 * Descriptor de una columna de la tabla, dirigido por datos.
 *
 * @param title título mostrado en la cabecera.
 * @param width ancho de la columna.
 * @param value texto a mostrar en la celda para un elemento [T].
 * @param enabled si la celda es clickeable para ese elemento (solo aplica si [update] != null).
 * @param update construye los parámetros de edición al pulsar la celda. `null` = columna de solo lectura.
 */
data class TableColumn<T>(
    val title: String,
    val width: Dp,
    val value: (T) -> String,
    val enabled: (T) -> Boolean = { true },
    val update: ((T) -> UpdateFieldParams)? = null
)

/**
 * Crea una columna a partir de un [FieldRegistration], usando su `displayName` y `dimensionWidth`.
 */
fun <T> registrationColumn(
    field: FieldRegistration,
    value: (T) -> String,
    enabled: (T) -> Boolean = { true },
    update: ((T) -> UpdateFieldParams)? = null
): TableColumn<T> = TableColumn(
    title = field.displayName,
    width = field.dimensionWidth,
    value = value,
    enabled = enabled,
    update = update
)

/**
 * Helper para construir [UpdateFieldParams] de un campo con títulos estándar.
 */
fun updateFieldParamsOf(
    field: FieldRegistration,
    id: Long,
    collection: String,
    idNetwork: String,
    value: String,
    typeField: TypeField,
    listOption: List<String> = emptyList()
): UpdateFieldParams = UpdateFieldParams(
    show = true,
    id = id,
    collection = collection,
    idNetwork = idNetwork,
    field = field,
    title = "Actualizar ${field.displayName}",
    description = "Ingresa el nuevo valor del campo ${field.displayName}",
    value = value,
    typeField = typeField,
    listOption = listOption
)

/**
 * Añade a un [LazyListScope] la cabecera de la tabla, las filas de datos y el estado vacío.
 * La cabecera y las filas comparten el mismo [scrollState] horizontal para desplazarse juntas.
 */
fun <T> LazyListScope.tableSection(
    columns: List<TableColumn<T>>,
    rows: List<T>,
    rowKey: (T) -> Any,
    scrollState: ScrollState,
    actionWidth: Dp,
    onUpdateClick: (UpdateFieldParams) -> Unit,
    emptyContent: @Composable () -> Unit,
    actionTitle: String = "Acción",
    rowAction: @Composable RowScope.(T) -> Unit
) {
    item {
        TableHeaderRow(
            modifier = Modifier.fillMaxWidth(),
            columns = columns,
            scrollState = scrollState,
            actionTitle = actionTitle,
            actionWidth = actionWidth
        )
    }

    items(items = rows, key = rowKey) { row ->
        TableDataRow(
            modifier = Modifier.fillMaxWidth().horizontalScroll(scrollState),
            item = row,
            columns = columns,
            actionWidth = actionWidth,
            onUpdateClick = onUpdateClick,
            action = { rowAction(row) }
        )
    }

    if (rows.isEmpty()) {
        item { emptyContent() }
    }
}

@Composable
fun <T> TableHeaderRow(
    modifier: Modifier = Modifier,
    columns: List<TableColumn<T>>,
    scrollState: ScrollState,
    actionTitle: String,
    actionWidth: Dp,
    typography: AppTypography = LocalAppTypographyCore.current
) {
    Row(
        modifier = modifier
            .clip(RoundedCornerShape(topStart = 8.dp, topEnd = 8.dp))
            .background(BackgroundTableBrand)
            .lineDividerSectionTable()
            .horizontalScroll(scrollState)
            .padding(12.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        columns.forEach { column ->
            Text(
                modifier = Modifier.width(column.width),
                text = column.title,
                color = DefaultTextColor,
                style = typography.bodyLarge
            )
        }

        Text(
            modifier = Modifier.widthIn(min = actionWidth),
            text = actionTitle,
            color = DefaultTextColor,
            style = typography.bodyLarge
        )
    }
}

@Composable
fun <T> TableDataRow(
    modifier: Modifier = Modifier,
    item: T,
    columns: List<TableColumn<T>>,
    actionWidth: Dp,
    onUpdateClick: (UpdateFieldParams) -> Unit,
    typography: AppTypography = LocalAppTypographyCore.current,
    action: @Composable RowScope.() -> Unit
) {
    Row(
        modifier = modifier
            .background(BackgroundOverlay)
            .lineDividerSectionTable()
            .padding(horizontal = 8.dp, vertical = 12.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        columns.forEach { column ->
            val update = column.update
            val cellModifier = if (update != null) {
                Modifier.width(column.width).clickable(enabled = column.enabled(item)) {
                    onUpdateClick(update(item))
                }
            } else {
                Modifier.width(column.width)
            }

            Text(
                modifier = cellModifier,
                text = column.value(item),
                color = SoftTextColor,
                style = typography.bodyLarge
            )
        }

        Row(
            modifier = Modifier.widthIn(min = actionWidth),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            content = action
        )
    }
}

fun Modifier.lineDividerSectionTable(): Modifier {
    return this.drawBehind(onDraw = {
        drawLine(
            color = DividerGrayColor,
            start = Offset(0f, size.height),
            end = Offset(size.width, size.height),
            strokeWidth = 2f
        )
    })
}
