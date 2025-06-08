package com.pe.losjardines.core.components.input_text

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.pe.losjardines.core.values.BlackTextColor
import com.pe.losjardines.core.values.FocusedBorderColor
import com.pe.losjardines.core.values.LocalAppTypography
import com.pe.losjardines.core.values.UnfocusedBorderColor
import com.pe.losjardines.utils.EMPTY

@Composable
fun InputText(
    modifier: Modifier = Modifier,
    textTitle: String = String.EMPTY,
    defaultText: String,
    onValueChange: (String) -> Unit = {},
    clickable: Boolean = false,
    onClick: () -> Unit = {},
    keyboardOptions: KeyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
){
    val typography = LocalAppTypography.current

    Column(modifier = modifier) {

        if(textTitle.isNotEmpty()){
            Text(
                text = textTitle,
                style = typography.titleSmall,
                color = BlackTextColor
            )

            Spacer(modifier = Modifier.height(8.dp))
        }

        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .clickable(
                    enabled = clickable,
                    onClick = onClick
            ),
            textStyle = typography.bodyLarge,
            value = defaultText,
            onValueChange = onValueChange,
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = FocusedBorderColor,
                unfocusedBorderColor = UnfocusedBorderColor,
                disabledBorderColor = UnfocusedBorderColor,
                disabledTextColor = BlackTextColor
            ),
            shape = RoundedCornerShape(4.dp),
            singleLine = true,
            enabled = !clickable,
            keyboardOptions = keyboardOptions
        )
   }
}