package com.pe.losjardines.core.base.error

sealed class Failure {

    data class NetworkFailure(val code: Int = 0, val message: String?) : Failure()
    data class ServerErrorClient(val code: Int = 0, val message: String?) : Failure()
    data class NotFoundResource(val code: Int = 0, val message: String?) : Failure()
    data class UnauthorizedOrForbidden(val code: Int = 0, val message: String?) : Failure()
    data class ServerBodyError(val code: Int = 0, val message: String?) : Failure()
    data class DatabaseFailure(val message: String?) : Failure()
    data class ValidationFailure(val message: String?, val field: String = "") : Failure()
    data class MapperToDomain(val exception: Exception?) : Failure()
    data class ServiceUncaughtFailure(val message: String?) : Failure()
    data class RequestNull(val message: String?) : Failure()
    data class UnknownFailure(val message: String?) : Failure()
    data class TimeOut(val message: String?) : Failure()
    data class SSLError(val message: String?) : Failure()

    companion object {
        fun fromThrowable(throwable: Throwable): Failure = when (throwable) {
            else -> UnknownFailure(throwable.message)
        }
    }

}