package com.kvxd.wellblechhack.module

import com.kvxd.wellblechhack.modules.AutoTotem
import com.kvxd.wellblechhack.util.SystemWithFile

object ModuleSystem: SystemWithFile("module") {

    private val modules = setOf<Module>(
        AutoTotem
    )

    operator fun get(moduleClazz: Class<Module>): Module? =
        modules.find { it::class == moduleClazz }

}