package com.pe.losjardines.base.usecase

import com.pe.losjardines.base.either.Either
import com.pe.losjardines.base.error.Failure

abstract class BaseSafeUseCase<in Params, out Type>: UseCase<Params, Type> {
    // Method that implement in each use case
    protected abstract suspend fun run(params: Params): Either<Failure, Type>

    // Method that execute flow
    override suspend fun execute(params: Params): Either<Failure, Type> {
        return runCatching {
            run(params)
        }.getOrElse {
            Either.Error(Failure.fromThrowable(it))
        }
    }
}   