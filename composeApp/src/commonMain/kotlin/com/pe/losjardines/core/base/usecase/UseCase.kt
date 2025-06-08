package com.pe.losjardines.core.base.usecase

import com.pe.losjardines.core.base.either.Either
import com.pe.losjardines.core.base.error.Failure
import kotlinx.coroutines.flow.Flow

interface UseCase<in P, out R> {
    fun execute(params: P): Flow<Either<Failure, R>>
}