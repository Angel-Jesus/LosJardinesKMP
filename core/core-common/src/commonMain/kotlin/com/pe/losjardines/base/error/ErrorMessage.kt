package com.pe.losjardines.base.error

import dev.gitlive.firebase.firestore.FirebaseFirestoreException

fun FirebaseAuthErrorType.getMessage(): String{
    return when(this){
        FirebaseAuthErrorType.INVALID_EMAIL -> "Please enter a valid email address"
        FirebaseAuthErrorType.WRONG_PASSWORD -> "Incorrect password. Please try again"
        FirebaseAuthErrorType.USER_NOT_FOUND -> "No account found with this email"
        FirebaseAuthErrorType.USER_DISABLED -> "This account has been disabled"
        FirebaseAuthErrorType.EMAIL_ALREADY_IN_USE -> "An account with this email already exists"
        FirebaseAuthErrorType.WEAK_PASSWORD -> "Password is too weak. Please use a stronger password"
        FirebaseAuthErrorType.OPERATION_NOT_ALLOWED -> "This operation is not allowed"
        FirebaseAuthErrorType.TOO_MANY_REQUESTS -> "Too many attempts. Please try again later"
        FirebaseAuthErrorType.NETWORK_ERROR -> "Network error. Please check your connection"
        FirebaseAuthErrorType.INVALID_CREDENTIAL -> "Invalid credentials provided"
        FirebaseAuthErrorType.ACCOUNT_EXISTS_WITH_DIFFERENT_CREDENTIAL ->
            "An account already exists with a different sign-in method"
        FirebaseAuthErrorType.REQUIRES_RECENT_LOGIN -> "Please log in again to continue"
        FirebaseAuthErrorType.CREDENTIAL_ALREADY_IN_USE -> "This credential is already in use"
        FirebaseAuthErrorType.TIMEOUT -> "Request timed out. Please try again"
        FirebaseAuthErrorType.INVALID_USER_TOKEN -> "Your session is invalid. Please log in again"
        FirebaseAuthErrorType.USER_TOKEN_EXPIRED -> "Your session has expired. Please log in again"
        FirebaseAuthErrorType.UNKNOWN ->"An unexpected error occurred"
    }
}


fun FirestoreErrorType.getMessage(): String{
    return when(this){
        FirestoreErrorType.PERMISSION_DENIED -> "You don't have permission to perform this action"
        FirestoreErrorType.NOT_FOUND -> "The requested data was not found"
        FirestoreErrorType.ALREADY_EXISTS -> "This data already exists"
        FirestoreErrorType.RESOURCE_EXHAUSTED -> "You've exceeded the usage limit. Please try again later"
        FirestoreErrorType.FAILED_PRECONDITION -> "This operation cannot be performed at this time"
        FirestoreErrorType.ABORTED -> "The operation was aborted. Please try again"
        FirestoreErrorType.OUT_OF_RANGE -> "The requested data is out of range"
        FirestoreErrorType.UNIMPLEMENTED -> "This feature is not yet implemented"
        FirestoreErrorType.INTERNAL -> "An internal error occurred. Please try again"
        FirestoreErrorType.UNAVAILABLE -> "Service is temporarily unavailable. Please check your connection"
        FirestoreErrorType.DATA_LOSS -> "Data loss detected. Please contact support"
        FirestoreErrorType.UNAUTHENTICATED -> "You need to sign in to perform this action"
        FirestoreErrorType.INVALID_ARGUMENT -> "Invalid data provided. Please check your input"
        FirestoreErrorType.DEADLINE_EXCEEDED -> "Request timed out. Please try again"
        FirestoreErrorType.CANCELLED -> "Operation was cancelled"
        FirestoreErrorType.UNKNOWN -> "An unexpected error occurred"
    }
}