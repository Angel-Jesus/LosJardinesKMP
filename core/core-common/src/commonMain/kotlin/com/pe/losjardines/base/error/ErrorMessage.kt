package com.pe.losjardines.base.error

fun Failure.getMessage(): String? {
    return when(this){
        is Failure.DatabaseFailure -> this.messageError
        is Failure.FirebaseAuthFailure -> this.messageError
        is Failure.FirestoreFailure -> this.messageError
        is Failure.InternetConnection -> this.messageError
        is Failure.MapperToDomain -> this.exception?.message
        is Failure.UnknownFailure -> this.messageError
    }
}
