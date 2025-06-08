package com.pe.losjardines.core.base.usecase

import com.pe.losjardines.core.base.either.Either
import com.pe.losjardines.core.base.error.Failure
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

abstract class BaseUseCase<in Params, out Type>: UseCase<Params, Type> {
    // Method that implement in each use case
    protected abstract suspend fun run(params: Params): Either<Failure, Type>

    // Method that execute flow
    override fun execute(params: Params): Flow<Either<Failure, Type>> = flow{
        val result = runCatching { run(params) }.getOrElse { Either.Error(Failure.fromThrowable(it)) }
        emit(result)
    }.catch { e -> emit(Either.Error(Failure.fromThrowable(e))) }
        .flowOn(Dispatchers.IO)
}