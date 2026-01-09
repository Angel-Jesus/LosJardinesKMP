package com.pe.losjardines.presentation.content.utils

enum class SEX(val option: String){
    MALE("Masculino"),
    FEMALE("Femenino");

    companion object{
        val options = entries.map { it.option }
    }
}
enum class DOCUMENT_TYPE(val type: String){
    DNI("DNI"),
    PASSPORT("Pasaporte"),
    CCI("CCI");

    companion object{
         val options = entries.map { it.type }
    }
}

