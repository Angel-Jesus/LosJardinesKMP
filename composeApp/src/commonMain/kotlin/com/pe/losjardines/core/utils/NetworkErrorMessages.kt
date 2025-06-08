package com.pe.losjardines.core.utils

object NetworkErrorMessages {

    const val UNKNOWN_ERROR = "Error desconocido en la respuesta."
    const val SOMETHING_UNEXPECTED_WENT_ERROR = "Ha sucedido un error inesperado."
    const val YOU_ARE_OFFLINE = "El dispositivo no cuenta con internet."
    const val BACKEND_DEFAULT_ERROR = "{\"message\":\"Error al parsear la respuesta\"}"
    const val BACKEND_ERROR_CODE = "Error desconocido en la respuesta."

    const val UNKNOWN = "Desconocido"
    const val PROCESSING_ERROR = "No se pudo procesar la respuesta."

    const val NETWORK_ERROR = "Error de red"
    const val DATABASE_ERROR = "Error de base de datos: %s"
    const val VALIDATION_ERROR = "Error de validación: %s"
    const val VALIDATION_ERROR_DEFAULT = "Datos inválidos"
    const val TIMEOUT_ERROR = "Tiempo de espera agotado. Verifica tu conexión a internet."
    const val SSL_ERROR = "Error de seguridad en la conexión."
    const val SERVICE_ERROR = "Error en el servicio: %s"
    const val INTERNAL_ERROR = "Error interno: No se pudo procesar la respuesta."

    fun formatWithCode(code: Int, message: String?): String {
        return if (code > 0) "Error $code: $message" else message ?: NETWORK_ERROR
    }

}