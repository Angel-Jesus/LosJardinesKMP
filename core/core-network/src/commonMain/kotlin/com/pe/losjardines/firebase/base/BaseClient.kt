package com.pe.losjardines.firebase.base

import com.pe.losjardines.base.either.Either
import com.pe.losjardines.base.error.Failure
import com.pe.losjardines.firebase.error.FirebaseAuthErrorType
import com.pe.losjardines.firebase.error.FirestoreErrorType
import com.pe.losjardines.firebase.error.getMessage
import com.pe.losjardines.utils.NetworkChecker
import dev.gitlive.firebase.auth.FirebaseAuthException
import dev.gitlive.firebase.firestore.FirebaseFirestoreException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.withContext
import kotlin.coroutines.cancellation.CancellationException

abstract class BaseClient(
    protected val networkChecker: NetworkChecker
) {
    protected suspend inline fun <reified T> callAuth(crossinline authCall: suspend () -> T): Either<Failure, T> {
        if(!networkChecker.isConnected()) return Either.Error(Failure.InternetConnection("Error connection"))

        return try {
            Either.Success(withContext(Dispatchers.IO){ authCall.invoke() })
        } catch (e: FirebaseAuthException) {
            val firebaseAuthType = FirebaseAuthErrorType.fromFirebaseAuthException(e)
            Either.Error(Failure.FirebaseAuthFailure(messageError = firebaseAuthType.getMessage()))
        } catch (e: CancellationException) {
            Either.Error(Failure.UnknownFailure(messageError = e.message))
        }
        catch (e: Exception) {
            Either.Error(Failure.UnknownFailure(messageError = e.message))
        }
    }

    protected suspend inline fun <reified T> callFirestore(crossinline firestoreCall: suspend () -> T): Either<Failure, T> {
        if(!networkChecker.isConnected()) return Either.Error(Failure.InternetConnection("Error connection"))

        return try {
            Either.Success(withContext(Dispatchers.IO){ firestoreCall.invoke() })
        } catch (e: FirebaseFirestoreException) {
            val firestoreType = FirestoreErrorType.fromFirestoreException(e)
            Either.Error(Failure.FirestoreFailure(messageError = firestoreType.getMessage()))
        } catch (e: CancellationException) {
            Either.Error(Failure.UnknownFailure(messageError = e.message))
        }
        catch (e: Exception) {
            Either.Error(Failure.UnknownFailure(messageError = e.message))
        }
    }
}
