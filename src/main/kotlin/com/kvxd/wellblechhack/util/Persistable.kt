package com.kvxd.wellblechhack.util

import net.minecraft.nbt.NbtCompound

interface Persistable<T> {

    fun load(tag: NbtCompound): T?
    fun save(tag: NbtCompound)

}