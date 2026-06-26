package com.pe.losjardines.base.database

import com.pe.losjardines.base.either.Either
import com.pe.losjardines.base.error.Failure
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.withContext

abstract class BaseDatabase {

    protected suspend inline fun <reified T> callDatabase(crossinline databaseCall: suspend () -> T?): Either<Failure, T> {
        return try {
            withContext(Dispatchers.IO){
                val value = databaseCall.invoke()
                if (value == null){
                    Either.Error(Failure.DatabaseFailure(messageError = "Value not found"))
                } else {
                    Either.Success(value)
                }
            }
        } catch (e: Exception){
            Either.Error(Failure.DatabaseFailure(messageError = e.message))
        }

    }
}