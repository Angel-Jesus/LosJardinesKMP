package com.pe.losjardines.base.either

import com.pe.losjardines.base.error.Failure

sealed class Either<out L, out R> {
    data class Error<out L>(val value: L) : Either<L, Nothing>()
    data class Success<out R>(val value: R) : Either<Nothing, R>()

    fun <T> either(
        fnL: (L) -> T,
        fnR: (R) -> T
    ): T {
        return when (this) {
            is Error -> fnL(value)
            is Success -> fnR(value)
        }
    }

    fun <L, R> Either<L, R>.getRightOrNull(): R? = if(this is Success) this.value else null
    fun <L, R> Either<L, R>.getLeftOrNull(): L? = if(this is Error) this.value else null

    fun fold(
        fnL: (L) -> Unit = {},
        fnR: (R) -> Unit = {}
    ) {
        when (this) {
            is Error -> fnL(value)
            is Success -> fnR(value)
        }
    }

    fun onSuccess(fn: (R) -> Unit): Either<L, R> {
        if (this is Success) fn(value)
        return this
    }

    fun onError(fn: (L) -> Unit): Either<L, R> {
        if (this is Error) fn(value)
        return this
    }

    fun <T> map(fn: (R) -> T): Either<L, T> {
        return when (this) {
            is Error -> Error(value)
            is Success -> Success(fn(value))
        }
    }

    fun <T> mapSafely(fn: (R) -> T): Either<Failure, T> {
        return when (this) {
            is Error -> Error(value as Failure)
            is Success -> {
                runCatching { fn(value) }
                    .fold(
                        onSuccess = { Success(it) },
                        onFailure = { Error(Failure.MapperToDomain(it as Exception)) }
                    )
            }
        }
    }
}