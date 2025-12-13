package com.pe.losjardines.base.error

sealed class Failure {
    data class FirebaseAuthFailure(val message: String) : Failure()
    data class FirestoreFailure(val message: String) : Failure()
    data class NetworkFailure(val code: Int = 0, val message: String?) : Failure()
    data class DatabaseFailure(val message: String?) : Failure()
    data class MapperToDomain(val exception: Exception?) : Failure()
    data class UnknownFailure(val message: String?) : Failure()

    companion object {
        fun fromThrowable(throwable: Throwable): Failure = when (throwable) {
            else -> UnknownFailure(throwable.message)
        }
    }
}