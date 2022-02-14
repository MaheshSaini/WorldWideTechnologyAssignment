package com.backbase.assignment.ui.network

import com.backbase.assignment.ui.data.model.MovieResult
import com.backbase.assignment.ui.network.exceptions.NoInternetException
import com.backbase.assignment.ui.network.exceptions.NotFoundException
import com.backbase.assignment.ui.network.exceptions.UnAuthorizedException
import com.backbase.assignment.ui.network.exceptions.UnKnownException
import retrofit2.HttpException
import retrofit2.Response
import java.net.HttpURLConnection
import java.net.SocketTimeoutException
import java.net.UnknownHostException

abstract class BaseService {

    protected suspend fun <T : Any> createCall(call: suspend () -> Response<T>): MovieResult<T> {

        val response: Response<T>
        try {
            response = call.invoke()
        } catch (t: Throwable) {
            t.printStackTrace()
            return MovieResult.Error(mapToNetworkError(t))
        }

        if (response.isSuccessful) {
            if (response.body() != null) {
                return MovieResult.Success(response.body()!!)
            }
        } else {
            val errorBody = response.errorBody()
            return if (errorBody != null) {
                MovieResult.Error(mapApiException(response.code()))
            } else MovieResult.Error(mapApiException(0))
        }
        return MovieResult.Error(HttpException(response))
    }

    private fun mapApiException(code: Int): Exception {
        return when (code) {
            HttpURLConnection.HTTP_NOT_FOUND -> NotFoundException()
            HttpURLConnection.HTTP_UNAUTHORIZED -> UnAuthorizedException()
            else -> UnKnownException()
        }
    }

    private fun mapToNetworkError(t: Throwable): Exception {
        return when (t) {
            is SocketTimeoutException
            -> SocketTimeoutException("Connection Timed Out")
            is UnknownHostException
            -> NoInternetException()
            else
            -> UnKnownException()

        }
    }
}