package com.pe.losjardines.core.base.extensions

import com.pe.losjardines.core.base.either.Either
import com.pe.losjardines.core.base.error.Failure
import kotlinx.coroutines.flow.Flow

suspend fun <T> Flow<Either<Failure, T>>.collectEither(
    onLoading: suspend () -> Unit = {},
    onSuccess: suspend (T) -> Unit = {},
    onError: suspend (Failure) -> Unit = {}
){
    this.collect{ result ->
        when(result){
            is Either.Success -> onSuccess(result.value)
            is Either.Error -> onError(result.value)
            is Either.Loading -> onLoading()
        }

    }
}