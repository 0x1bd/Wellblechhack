package com.kvxd.wellblechhack.module

import com.kvxd.wellblechhack.modules.AutoTotem
import com.kvxd.wellblechhack.util.SystemWithFile
import net.minecraft.nbt.NbtCompound
import net.minecraft.nbt.NbtIo

object ModuleSystem : SystemWithFile("module") {

    private val modules = setOf<Module>(
        AutoTotem
    )

    operator fun get(moduleClazz: Class<out Module>): Module? = modules.find { it::class == moduleClazz }

    override fun initialize() {
        super.initialize()
        load()
    }

    fun load() {
        val tag = if (FILE.readText().isEmpty()) NbtCompound()
        else NbtIo.read(FILE.toPath()) ?: return

        modules.forEach { module ->
            val moduleTag = tag.getCompound(module.name)
            module.load(moduleTag)
        }
    }

    fun save() {
        val tag = NbtCompound()

        modules.forEach { module ->
            module.save(tag)
        }

        NbtIo.write(tag, FILE.toPath())
    }
}