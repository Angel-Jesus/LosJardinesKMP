package com.pe.losjardines.base.usecase

import com.pe.losjardines.base.either.Either
import com.pe.losjardines.base.error.Failure

interface UseCase<in P, out R> {
    suspend fun execute(params: P): Either<Failure, R>
}