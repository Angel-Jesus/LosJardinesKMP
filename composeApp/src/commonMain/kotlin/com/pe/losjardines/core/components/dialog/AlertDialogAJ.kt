package com.pe.losjardines.core.components.dialog

import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import com.pe.losjardines.core.values.BackgroundBrandColor
import com.pe.losjardines.core.values.BrandTextColor

@Composable
fun AlertDialogAJ(
    title: String,
    message: String,
    icon: ImageVector,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit
){
    AlertDialog(
        icon = {
            Icon(
                icon,
                contentDescription = "alertDialog"
            )
        },
        iconContentColor = BackgroundBrandColor,
        title = {
            Text(text = title)
        },
        text = {
            Text(text = message)
        },
        containerColor = Color.White,
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(
                onClick = onConfirm,
                colors = ButtonDefaults.textButtonColors(
                    contentColor = BrandTextColor
                )
            ) {
                Text("Aceptar")
            }
        },
        dismissButton = {
            TextButton(
                onClick = onDismiss,
                colors = ButtonDefaults.textButtonColors(
                    contentColor = BrandTextColor
                )
            ) {
                Text("Cancelar")
            }
        }
    )
}