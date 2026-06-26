package com.pe.losjardines.base.error

sealed class Failure: Throwable() {
    data class FirebaseAuthFailure(val messageError: String) : Failure()
    data class FirestoreFailure(val messageError: String) : Failure()
    data class DatabaseFailure(val messageError: String?) : Failure()
    data class MapperToDomain(val exception: Exception?) : Failure()
    data class InternetConnection(val messageError: String?) : Failure()
    data class UnknownFailure(val messageError: String?) : Failure()

    companion object {
        fun fromThrowable(throwable: Throwable): Failure = when (throwable) {
            else -> UnknownFailure(throwable.message)
        }
    }
}