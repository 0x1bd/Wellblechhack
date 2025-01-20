package com.kvxd.wellblechhack.util

import com.kvxd.wellblechhack.Wellblechhack
import java.io.File

abstract class SystemWithFile(id: String, val hasDirectory: Boolean = false) {

    val FILE = File(Wellblechhack.ROOT_FILE, id)
    val DIRECTORY = if (hasDirectory) File(Wellblechhack.ROOT_FILE, id + "s") else null

    fun getFiles(): Array<File>? = DIRECTORY?.listFiles()

    fun initialize() {
        FILE.createNewFile()

        DIRECTORY?.let {
            if (it.exists())
                it.delete()
        }

        DIRECTORY?.mkdir()
    }

}