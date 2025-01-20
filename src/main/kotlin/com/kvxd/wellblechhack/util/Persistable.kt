package com.kvxd.wellblechhack.util

import net.minecraft.nbt.NbtCompound

interface Persistable {

    fun load(tag: NbtCompound)
    fun save(tag: NbtCompound)

}