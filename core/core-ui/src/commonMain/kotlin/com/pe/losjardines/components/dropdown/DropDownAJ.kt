package com.pe.losjardines.components.dropdown

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MenuAnchorType
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.pe.losjardines.values.AppTypography
import com.pe.losjardines.values.FocusedBorderColor
import com.pe.losjardines.values.LocalAppTypographyCore
import com.pe.losjardines.values.SoftTextColor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DropDownAJ(
    modifier: Modifier = Modifier,
    label: String? = null,
    options: List<String>,
    selectedOption: String,
    placeholder: String? = null,
    onOptionSelected: (String) -> Unit,
    enabled: Boolean = true,
    typography: AppTypography = LocalAppTypographyCore.current
){
    var expanded by remember { mutableStateOf(false) }

    Column(modifier = modifier) {

        label?.let {
            Text(
                text = label,
                style = typography.titleMedium
            )
            Spacer(modifier = Modifier.height(8.dp))
        }

        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { if (enabled) expanded = !expanded },
            modifier = Modifier.wrapContentWidth()
        ) {

            OutlinedTextField(
                value = selectedOption,
                onValueChange = {},
                readOnly = true,
                enabled = enabled,
                textStyle = typography.bodyLarge,
                placeholder = if(placeholder != null) {
                    @Composable {
                        Text(
                            text = placeholder,
                            color = SoftTextColor,
                            style = typography.titleSmall
                        )
                    }
                } else { null },
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
                },
                modifier = Modifier
                    .menuAnchor(MenuAnchorType.PrimaryNotEditable)
                    .fillMaxWidth(),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    focusedPlaceholderColor = FocusedBorderColor,
                    focusedIndicatorColor = FocusedBorderColor
                )
            )

            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                options.forEach { option ->
                    DropdownMenuItem(
                        text = { Text(option) },
                        onClick = {
                            onOptionSelected(option)
                            expanded = false
                        }
                    )
                }
            }
        }
    }

}