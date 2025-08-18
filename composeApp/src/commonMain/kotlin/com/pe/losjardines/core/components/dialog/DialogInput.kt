package com.pe.losjardines.core.components.dialog

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.pe.losjardines.core.components.button.EndingButton
import com.pe.losjardines.core.components.input_text.InputText
import com.pe.losjardines.core.components.picker.Picker
import com.pe.losjardines.presentation.enums.TitleClient
import com.pe.losjardines.utils.companions.EMPTY

@Composable
fun DialogInput(
    modifier: Modifier = Modifier,
    title: String,
    keyboardType: KeyboardOptions = KeyboardOptions.Default,
    isClickableInput : Boolean = false,
    typeValue : TitleClient,
    defaultText: String = String.EMPTY,
    onSave: (inputValue: String) -> Unit,
    onDismiss: () -> Unit
){
    var inputValue by remember { mutableStateOf(defaultText) }
    val showPicker = remember { mutableStateOf(false) }

    if(showPicker.value){
        if(typeValue == TitleClient.DATE){
            Picker.date(showPicker, onDateSelected = { inputValue = it })
        }
        else if(typeValue == TitleClient.TIME){
            Picker.time(showPicker, onTimeSelected = { inputValue = it })
        }
    }

    Dialog(
        properties = DialogProperties(
            usePlatformDefaultWidth = false
        ),
        onDismissRequest = onDismiss
    ){
        Card(
            modifier = modifier,
            shape = RoundedCornerShape(8.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White)
        ) {
            Column(modifier = Modifier.fillMaxWidth().padding(16.dp)) {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = "Actualizar $title",
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(16.dp))

                InputText(
                    modifier = Modifier.fillMaxWidth(),
                    defaultText = inputValue,
                    onValueChange = { newValue ->
                        inputValue = newValue
                    },
                    onClick = {
                        showPicker.value = true
                    },
                    clickable = isClickableInput,
                    keyboardOptions = keyboardType
                )

                Spacer(modifier = Modifier.height(14.dp))

                Row {
                    EndingButton(
                        modifier = Modifier.weight(1f).padding(horizontal = 16.dp),
                        text = "Cancelar",
                        onClick = onDismiss
                    )

                    Spacer(modifier = Modifier.width(4.dp))

                    EndingButton(
                        modifier = Modifier.weight(1f).padding(horizontal = 16.dp),
                        text = "Guardar",
                        onClick = { onSave(inputValue) }
                    )
                }
            }
        }
    }
}
