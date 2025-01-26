package com.kvxd.wellblechhack.module

import com.kvxd.wellblechhack.modules.AutoTotem
import com.kvxd.wellblechhack.modules.ClickGuiModule
import com.kvxd.wellblechhack.modules.SuppressModule
import org.json.JSONArray
import org.json.JSONObject

enum class Category(
    private val modules: List<Module>,
) {

    PLAYER(listOf(
        AutoTotem
    )),
    RENDER(listOf(

    )),
    WORLD(listOf(

    )),
    MOVEMENT(listOf(

    )),
    EXPLOIT(listOf(

    )),
    THE_BIN(listOf(
        ClickGuiModule,
        SuppressModule
    ));

    fun toJson(): JSONObject {
        val json = JSONObject()
        val modulesJson = JSONArray()
        modules.forEach { module ->
            modulesJson.put(module.parseJson(JSONObject()))
        }
        json.put("modules", modulesJson)
        return json
    }

}