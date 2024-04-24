package com.ougi.callme.presentation.routing

import io.ktor.server.routing.*

fun Routing.configureUserRouting() {
    route("/user") {
        create()
        get()
        update()
    }
}

private fun Route.create() = post("/create") {}.proceedRoute(USER_SERVICE_NAME)
private fun Route.get() = get("/get") {}.proceedRoute(USER_SERVICE_NAME)
private fun Route.update() = post("/update") {}.proceedRoute(USER_SERVICE_NAME)


private const val USER_SERVICE_NAME = "callme-user"