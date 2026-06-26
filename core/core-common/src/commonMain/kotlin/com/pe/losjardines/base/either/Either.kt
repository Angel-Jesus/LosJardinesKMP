package com.pe.losjardines.base.either

import com.pe.losjardines.base.error.Failure

sealed class Either<out L, out R> {
    data class Error<out L>(val error: L) : Either<L, Nothing>()
    data class Success<out R>(val data: R) : Either<Nothing, R>()

    fun <T> either(
        fnL: (L) -> T,
        fnR: (R) -> T
    ): T {
        return when (this) {
            is Error -> fnL(error)
            is Success -> fnR(data)
        }
    }

    fun <L, R> Either<L, R>.getRightOrNull(): R? = if(this is Success) this.data else null
    fun <L, R> Either<L, R>.getLeftOrNull(): L? = if(this is Error) this.error else null

    fun fold(
        fnL: (L) -> Unit = {},
        fnR: (R) -> Unit = {}
    ) {
        when (this) {
            is Error -> fnL(error)
            is Success -> fnR(data)
        }
    }

    fun onSuccess(fn: (R) -> Unit): Either<L, R> {
        if (this is Success) fn(data)
        return this
    }

    fun onError(fn: (L) -> Unit): Either<L, R> {
        if (this is Error) fn(error)
        return this
    }

    fun <T> map(fn: (R) -> T): Either<L, T> {
        return when (this) {
            is Error -> Error(error)
            is Success -> Success(fn(data))
        }
    }

    fun <T> mapSafely(fn: (R) -> T): Either<Failure, T> {
        return when (this) {
            is Error -> Error(error as Failure)
            is Success -> {
                runCatching { fn(data) }
                    .fold(
                        onSuccess = { Success(it) },
                        onFailure = { Error(Failure.MapperToDomain(it as Exception)) }
                    )
            }
        }
    }
}