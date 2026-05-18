package com.pe.losjardines.preview

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.pe.losjardines.components.dialog.ResultDialog
import com.pe.losjardines.values.AppTheme

@Preview(showBackground = true)
@Composable
fun ResultDialogPreview(){
    AppTheme {
        ResultDialog(
            modifier = Modifier,
            title = "Proceso del registro",
            description = "Información del cliente guardado correctamente",
            isSuccess = true,
            visibility = true,
            onDismiss = {

            }
        )
    }
}