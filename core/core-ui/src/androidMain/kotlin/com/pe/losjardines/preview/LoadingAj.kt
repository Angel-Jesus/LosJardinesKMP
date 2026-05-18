package com.pe.losjardines.preview

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.pe.losjardines.components.loading.LoadingAJ
import com.pe.losjardines.values.AppTheme

@Preview(showBackground = true)
@Composable
fun LoadingAJPreview(){
    AppTheme {
        LoadingAJ()
    }
}