package com.kvxd.wellblechhack.util

import java.io.File
import kotlin.reflect.KProperty

open class FileHierarchy(private val root: File) {

    inner class DirDelegate(
        private val parentProvider: () -> File,
        private val name: String,
    ) {
        operator fun getValue(thisRef: Any?, property: KProperty<*>): File {
            return parentProvider().resolve(name)
        }
    }

    fun dir(name: String) = DirDelegate({ root }, name)

    protected fun File.dir(name: String) = DirDelegate({ this }, name)
}