package com.example.data.base

import com.example.domain.network.Response
import io.ktor.client.plugins.*
import java.io.IOException

inline fun <reified T : Any> safeRequest(
    request: () -> T,
): Response<T> =
    try {
        Response.Success(request())
    } catch (e: ResponseException) {
        Response.Failure.ApiError(e)
    } catch (e: IOException) {
        Response.Failure.NoInternetError(e)
    } catch (e: Exception) {
        Response.Failure.UnknownError(e)
    }
