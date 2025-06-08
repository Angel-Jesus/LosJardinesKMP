package com.pe.losjardines.core.base.network

import com.pe.losjardines.core.base.either.Either
import com.pe.losjardines.core.base.error.Failure
import com.pe.losjardines.core.utils.ConnectionUtils
import com.pe.losjardines.core.utils.NetworkErrorMessages
import io.ktor.client.call.body
import io.ktor.client.plugins.ResponseException
import io.ktor.client.statement.HttpResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.withContext

abstract class BaseClient(
    val connectionUtils: ConnectionUtils
) {
    protected suspend inline fun <reified T> callService(crossinline ktorCall: suspend () -> HttpResponse): Either<Failure, T>{
        return when(connectionUtils.isNetworkAvailable()){
            true ->{
                try {
                    withContext(Dispatchers.IO){
                        val response = ktorCall.invoke().body<T>()
                        if(T::class == Boolean::class) {
                            Either.Success(true)
                        }
                        Either.Success(response)
                    }
                }catch (e: ResponseException){
                    val statusCode = e.response.status.value
                    val message = e.response.status.description
                    val failure = getErrorMessageFromServer(message, statusCode)
                    Either.Error(failure)
                }
            }
            false ->{
                Either.Error(Failure.NetworkFailure(message = NetworkErrorMessages.YOU_ARE_OFFLINE))
            }
        }
    }

    suspend fun getErrorMessageFromServer(message: String, code: Int? = null): Failure {
        return if (code != null) {
            withContext(Dispatchers.IO){
                when (code) {
                    400 -> Failure.ServerErrorClient(code, message)
                    401 -> Failure.UnauthorizedOrForbidden(code, message)
                    404, 409 -> Failure.NotFoundResource(code, message)
                    else -> Failure.ServerBodyError(code, message)
                }
            }
        } else {
            Failure.UnknownFailure(message = message)
        }
    }
}