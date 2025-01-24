package com.kvxd.wellblechhack.web.api

import com.kvxd.wellblechhack.web.api.routes.moduleRoutes
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun runApiServer() {
    embeddedServer(Netty, port = 1337) {
        routing {
            get("/") {
                call.respond("Wellblechhack.")
            }

            moduleRoutes()
        }
    }.start(true)
}