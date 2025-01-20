package com.kvxd.wellblechhack.setting

import com.kvxd.wellblechhack.util.Persistable
import net.minecraft.nbt.NbtCompound
import kotlin.reflect.KProperty

abstract class Setting<T>(
    val name: String,
    var display: String,
    private val default: T,
    var value: T,
): Persistable<T> {

    fun reset() {
        this.value = default
    }

    abstract fun validate(value: T): Boolean

    fun toTag(): NbtCompound {
        val tag = NbtCompound()

        tag.putString("name", name)
        save(tag)

        return tag
    }

    fun fromTag(tag: NbtCompound): T? {
        val value = load(tag)

        return value
    }

    open operator fun getValue(any: Any, kProperty: KProperty<*>): T = value

}