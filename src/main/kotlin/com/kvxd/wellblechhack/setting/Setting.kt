package com.kvxd.wellblechhack.setting

import com.kvxd.wellblechhack.module.Module
import com.kvxd.wellblechhack.util.Persistable
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

abstract class Setting<T>(
    val name: String,
    var display: String,
    private val default: T,
    var value: T,
): Persistable, ReadWriteProperty<Module, T> {

    fun reset() {
        this.value = default
    }

    override fun getValue(thisRef: Module, property: KProperty<*>): T {
        return value
    }

    override fun setValue(thisRef: Module, property: KProperty<*>, value: T) {
        this.value = value
    }
}