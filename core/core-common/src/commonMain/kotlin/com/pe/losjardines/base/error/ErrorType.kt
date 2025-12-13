package com.pe.losjardines.base.error

import dev.gitlive.firebase.auth.FirebaseAuthException
import dev.gitlive.firebase.firestore.FirebaseFirestoreException

enum class FirebaseAuthErrorType {
    INVALID_EMAIL,
    WRONG_PASSWORD,
    USER_NOT_FOUND,
    USER_DISABLED,
    EMAIL_ALREADY_IN_USE,
    WEAK_PASSWORD,
    OPERATION_NOT_ALLOWED,
    TOO_MANY_REQUESTS,
    NETWORK_ERROR,
    INVALID_CREDENTIAL,
    ACCOUNT_EXISTS_WITH_DIFFERENT_CREDENTIAL,
    REQUIRES_RECENT_LOGIN,
    CREDENTIAL_ALREADY_IN_USE,
    TIMEOUT,
    INVALID_USER_TOKEN,
    USER_TOKEN_EXPIRED,
    UNKNOWN;

    companion object {
        fun fromFirebaseAuthException(exception: FirebaseAuthException): FirebaseAuthErrorType {
            val message = exception.message?.lowercase() ?: ""
            val cause = exception.cause?.message?.lowercase() ?: ""
            val fullMessage = "$message $cause"

            return when {
                fullMessage.contains("invalid-email") ||
                        fullMessage.contains("invalid email") ||
                        fullMessage.contains("badly formatted") -> INVALID_EMAIL

                fullMessage.contains("wrong-password") ||
                        fullMessage.contains("incorrect password") ||
                        fullMessage.contains("password is invalid") -> WRONG_PASSWORD

                fullMessage.contains("user-not-found") ||
                        fullMessage.contains("no user record") ||
                        fullMessage.contains("no user found") -> USER_NOT_FOUND

                fullMessage.contains("user-disabled") ||
                        fullMessage.contains("account has been disabled") -> USER_DISABLED

                fullMessage.contains("email-already-in-use") ||
                        fullMessage.contains("email address is already") -> EMAIL_ALREADY_IN_USE

                fullMessage.contains("weak-password") ||
                        fullMessage.contains("password should be at least") -> WEAK_PASSWORD

                fullMessage.contains("operation-not-allowed") ||
                        fullMessage.contains("operation is not allowed") -> OPERATION_NOT_ALLOWED

                fullMessage.contains("too-many-requests") ||
                        fullMessage.contains("blocked due to unusual activity") -> TOO_MANY_REQUESTS

                fullMessage.contains("network") ||
                        fullMessage.contains("unable to resolve host") ||
                        fullMessage.contains("network request failed") -> NETWORK_ERROR

                fullMessage.contains("invalid-credential") ||
                        fullMessage.contains("credential is malformed") -> INVALID_CREDENTIAL

                fullMessage.contains("account-exists-with-different-credential") ||
                        fullMessage.contains("exists with different sign-in credentials") ->
                    ACCOUNT_EXISTS_WITH_DIFFERENT_CREDENTIAL

                fullMessage.contains("requires-recent-login") ||
                        fullMessage.contains("requires recent authentication") -> REQUIRES_RECENT_LOGIN

                fullMessage.contains("credential-already-in-use") -> CREDENTIAL_ALREADY_IN_USE

                fullMessage.contains("timeout") ||
                        fullMessage.contains("deadline exceeded") -> TIMEOUT

                fullMessage.contains("invalid-user-token") ||
                        fullMessage.contains("user token has expired") -> INVALID_USER_TOKEN

                fullMessage.contains("user-token-expired") -> USER_TOKEN_EXPIRED

                else -> UNKNOWN
            }
        }
    }
}

enum class FirestoreErrorType {
    PERMISSION_DENIED,
    NOT_FOUND,
    ALREADY_EXISTS,
    RESOURCE_EXHAUSTED,
    FAILED_PRECONDITION,
    ABORTED,
    OUT_OF_RANGE,
    UNIMPLEMENTED,
    INTERNAL,
    UNAVAILABLE,
    DATA_LOSS,
    UNAUTHENTICATED,
    INVALID_ARGUMENT,
    DEADLINE_EXCEEDED,
    CANCELLED,
    UNKNOWN;

    companion object {
        fun fromFirestoreException(exception: FirebaseFirestoreException): FirestoreErrorType {
            // GitLive FirebaseFirestoreException only has message, cause, and suppressedExceptions
            // We need to parse the message to determine the error type
            val message = exception.message?.lowercase() ?: ""
            val cause = exception.cause?.message?.lowercase() ?: ""
            val fullMessage = "$message $cause"

            return when {
                fullMessage.contains("permission-denied") ||
                        fullMessage.contains("permission denied") ||
                        fullMessage.contains("missing or insufficient permissions") -> PERMISSION_DENIED

                fullMessage.contains("not-found") ||
                        fullMessage.contains("not found") ||
                        fullMessage.contains("no document to update") -> NOT_FOUND

                fullMessage.contains("already-exists") ||
                        fullMessage.contains("already exists") ||
                        fullMessage.contains("document already exists") -> ALREADY_EXISTS

                fullMessage.contains("resource-exhausted") ||
                        fullMessage.contains("quota exceeded") ||
                        fullMessage.contains("rate limit") -> RESOURCE_EXHAUSTED

                fullMessage.contains("failed-precondition") ||
                        fullMessage.contains("failed precondition") ||
                        fullMessage.contains("requires an index") -> FAILED_PRECONDITION

                fullMessage.contains("aborted") ||
                        fullMessage.contains("transaction") && fullMessage.contains("conflict") -> ABORTED

                fullMessage.contains("out-of-range") ||
                        fullMessage.contains("out of range") -> OUT_OF_RANGE

                fullMessage.contains("unimplemented") ||
                        fullMessage.contains("not implemented") -> UNIMPLEMENTED

                fullMessage.contains("internal") ||
                        fullMessage.contains("internal error") -> INTERNAL

                fullMessage.contains("unavailable") ||
                        fullMessage.contains("service unavailable") ||
                        fullMessage.contains("network") ||
                        fullMessage.contains("unable to resolve host") -> UNAVAILABLE

                fullMessage.contains("data-loss") ||
                        fullMessage.contains("data loss") -> DATA_LOSS

                fullMessage.contains("unauthenticated") ||
                        fullMessage.contains("not authenticated") ||
                        fullMessage.contains("request not authenticated") -> UNAUTHENTICATED

                fullMessage.contains("invalid-argument") ||
                        fullMessage.contains("invalid argument") ||
                        fullMessage.contains("invalid data") -> INVALID_ARGUMENT

                fullMessage.contains("deadline-exceeded") ||
                        fullMessage.contains("deadline exceeded") ||
                        fullMessage.contains("timeout") -> DEADLINE_EXCEEDED

                fullMessage.contains("cancelled") ||
                        fullMessage.contains("operation was cancelled") -> CANCELLED

                else -> UNKNOWN
            }
        }
    }
}