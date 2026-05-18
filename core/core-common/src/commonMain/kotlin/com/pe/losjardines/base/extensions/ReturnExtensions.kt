package com.pe.losjardines.base.extensions

import com.pe.losjardines.base.either.Either
import com.pe.losjardines.base.error.Failure
import kotlinx.coroutines.flow.Flow


suspend fun <T> Flow<Either<Failure, T>>.collectEither(
    onSuccess: suspend (T) -> Unit = {},
    onError: suspend (Failure) -> Unit = {}
){
    this.collect{ result ->
        when(result){
            is Either.Success -> onSuccess(result.value)
            is Either.Error -> onError(result.value)
        }

    }
}

suspend fun <T> Either<Failure, T>.collectEither(
    onSuccess: suspend (T) -> Unit = {},
    onError: suspend (Failure) -> Unit = {}
){
    when(this){
        is Either.Success -> onSuccess(this.value)
        is Either.Error -> onError(this.value)
    }
}