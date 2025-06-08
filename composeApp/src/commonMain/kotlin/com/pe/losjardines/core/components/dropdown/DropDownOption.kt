package com.pe.losjardines.core.components.dropdown

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize
import com.pe.losjardines.core.values.LocalAppTypography
import com.pe.losjardines.utils.EMPTY

@Composable
fun DropDownOption(
    modifier: Modifier = Modifier,
    title: String = String.EMPTY,
    style: TextStyle = LocalAppTypography.current.titleMedium,
    itemList: List<String>,
    itemDefault: String,
    itemSelected: (String) -> Unit
){
    var expanded by remember { mutableStateOf(false) }
    var textFiledSize by remember { mutableStateOf(IntSize.Zero) }

    val icon = if (expanded) {
        Icons.Filled.KeyboardArrowUp
    } else {
        Icons.Filled.KeyboardArrowDown
    }

    val density = LocalDensity.current
    val dropdownWidth = with(density) { textFiledSize.width.toDp() }

    Column(modifier = modifier) {

        if(title.isNotEmpty()){
            Text(
                text = title,
                style = style
            )

            Spacer(modifier = Modifier.height(14.dp))
        }

        OutlinedTextField(
            value = itemDefault,
            onValueChange = { },
            enabled = false,
            singleLine = true,
            modifier = Modifier
                .fillMaxWidth()
                .onGloballyPositioned { coordinates ->
                    textFiledSize = coordinates.size
                }
                .clickable { expanded = !expanded },
            trailingIcon = {
                Icon(
                    imageVector = icon,
                    contentDescription = "Icon Filter",
                    modifier = Modifier.clickable { expanded = !expanded },
                    tint = Color.Black
                )
            },
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color.Red,
                unfocusedBorderColor = Color.Red,
                unfocusedTextColor = Color.Black,
                disabledTextColor = Color.Black,
                disabledBorderColor = Color.Black
            ),
        )

        DropdownMenu(
            modifier = Modifier.width(dropdownWidth),
            expanded = expanded,
            containerColor = Color.White,
            onDismissRequest = { expanded = false },
        ) {
            itemList.forEach { label ->
                DropdownMenuItem(
                    text = { Text(text = label) },
                    onClick = {
                        expanded = false
                        itemSelected(label)
                    }
                )
            }
        }
    }
}