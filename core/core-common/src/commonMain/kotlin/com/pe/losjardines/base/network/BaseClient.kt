package com.pe.losjardines.base.network

import com.pe.losjardines.base.either.Either
import com.pe.losjardines.base.error.Failure
import com.pe.losjardines.base.error.FirebaseAuthErrorType
import com.pe.losjardines.base.error.FirestoreErrorType
import com.pe.losjardines.base.error.getMessage
import dev.gitlive.firebase.auth.FirebaseAuthException
import dev.gitlive.firebase.firestore.FirebaseFirestoreException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.withContext

abstract class BaseClient{
    protected suspend inline fun <reified T> callAuth(crossinline authCall: suspend () -> T): Either<Failure, T>{
        return try {
            withContext(Dispatchers.IO){
                Either.Success(authCall.invoke())
            }
        } catch (e: FirebaseAuthException){
            val firebaseAuthType = FirebaseAuthErrorType.fromFirebaseAuthException(e)
            Either.Error(Failure.FirebaseAuthFailure(message = firebaseAuthType.getMessage()))
        } catch (e: Exception){
            Either.Error(Failure.UnknownFailure(message = e.message))
        }
    }

    protected suspend inline fun <reified T> callFirestore(crossinline firestoreCall: suspend () -> T): Either<Failure, T>{
        return try {
            withContext(Dispatchers.IO){
                Either.Success(firestoreCall.invoke())
            }
        } catch (e: FirebaseFirestoreException){
            val firestoreType = FirestoreErrorType.fromFirestoreException(e)
            Either.Error(Failure.FirestoreFailure(message = firestoreType.getMessage()))
        } catch (e: Exception){
            Either.Error(Failure.UnknownFailure(message = e.message))
        }
    }
}