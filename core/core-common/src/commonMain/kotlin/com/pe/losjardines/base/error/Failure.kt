package com.pe.losjardines.base.error

sealed class Failure {
    data class FirebaseAuthFailure(val messageError: String) : Failure()
    data class FirestoreFailure(val messageError: String) : Failure()
    data class DatabaseFailure(val messageError: String?) : Failure()
    data class MapperToDomain(val exception: Exception?) : Failure()
    data class InternetConnection(val messageError: String?) : Failure()
    data class UnknownFailure(val messageError: String?) : Failure()
}

class FailureException(val failure: Failure) : Exception(failure.getMessage())

fun Failure.toException(): FailureException = FailureException(this)

fun Throwable.toFailure(): Failure =
    (this as? FailureException)?.failure ?: Failure.UnknownFailure(message)
