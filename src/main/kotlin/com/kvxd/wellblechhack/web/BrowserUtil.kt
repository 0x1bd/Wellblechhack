package com.kvxd.wellblechhack.web

import com.cinemamod.mcef.MCEF

object BrowserUtil {

    private const val INITIAL_URL = "http://localhost:5173"

    var FRAMERATE_CAP = 144

    fun generate(url: String? = null): MCEFScreen {
        val browser = MCEF.createBrowser(url ?: INITIAL_URL, true)
        val screen = MCEFScreen(browser)
        return screen
    }

}