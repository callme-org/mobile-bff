package com.ougi.callme.domain.model

import io.ktor.http.*

sealed interface AuthResponse {

    data object Authenticated : AuthResponse

    class Failure(
        val code: HttpStatusCode,
        val body: ByteArray
    ) : AuthResponse
}