package com.pe.losjardines.presentation.content.utils

enum class Sex(val option: String, val abbreviations: String){
    MALE("Masculino", "M"),
    FEMALE("Femenino", "F");

    companion object{
        val options = entries.map { it.option }

        fun getAbbreviations(option: String): String{
            return entries.find { it.option == option }?.abbreviations ?: ""
        }
    }
}
enum class DocumentType(val type: String){
    DNI("DNI"),
    PASSPORT("Pasaporte"),
    CCI("CCI");

    companion object{
         val options = entries.map { it.type }
    }
}

