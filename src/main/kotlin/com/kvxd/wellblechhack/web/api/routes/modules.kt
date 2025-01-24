package com.kvxd.wellblechhack.web.api.routes

import com.kvxd.wellblechhack.module.Module
import com.kvxd.wellblechhack.module.ModuleSystem
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.json.JsonObject
import org.json.JSONArray
import org.json.JSONObject

/**
 *         val moduleName = call.parameters["moduleName"] ?: return@get call.respondText("Missing moduleName")
 *
 *         ModuleSystem.modules.find { it.name == moduleName }
 *
 *
 *         call.respondText("Settings for $moduleName")
 */

fun Route.moduleRoutes() {
    get("/module/list") {
        val modulesArray = JSONArray()

        ModuleSystem.modules.forEach { module ->
            val moduleJson = JSONObject()
            module.parseJson(moduleJson)
            modulesArray.put(moduleJson)
        }

        call.respondText(modulesArray.toString())
    }
}