package com.kvxd.wellblechhack.util

import com.kvxd.wellblechhack.Wellblechhack
import java.io.File

abstract class SystemWithFile(id: String, hasDirectory: Boolean = false) {

    val FILE = File(Wellblechhack.ROOT_FILE, "$id.nbt")
    val DIRECTORY = if (hasDirectory) File(Wellblechhack.ROOT_FILE, id + "s") else null

    fun getFiles(): Array<File>? = DIRECTORY?.listFiles()

    open fun initialize() {
        FILE.createNewFile()

        DIRECTORY?.let {
            if (it.exists())
                it.delete()
        }

        DIRECTORY?.mkdir()
    }

}