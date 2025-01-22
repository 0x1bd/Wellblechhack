package com.kvxd.wellblechhack.module

enum class Category {

    PLAYER,
    RENDER,
    WORLD,
    MOVEMENT,
    EXPLOIT,
    THE_BIN;

    fun filterModules() =
        ModuleSystem.modules.filter { it.category == this }

}