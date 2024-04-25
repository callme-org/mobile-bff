package com.ougi.callme.presentation.routing

import com.ougi.callme.domain.model.AuthResponse
import com.ougi.callme.domain.usecase.AuthRequestUseCase
import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject

fun Application.configureRouting() =
    routing {
        route("/m") {
            // TODO routing here
        }
    }

fun Route.proceedRoute(targetServiceName: String) {
    val authRequestUseCase by inject<AuthRequestUseCase>()
    val client by inject<HttpClient>()
    handle {
        when (val response = authRequestUseCase.authenticateRequest(call.request.headers)) {
            AuthResponse.Authenticated ->
                client.request("http://$targetServiceName:8080/${call.request.uri}") {
                    method = call.request.httpMethod
                    headers.appendAll(call.request.headers)
                    setBody(call.receiveText())
                }
                    .let { call.respond(it.status, it.readBytes()) }

            is AuthResponse.Failure -> call.respond(response.code, response.body)
        }

    }
}