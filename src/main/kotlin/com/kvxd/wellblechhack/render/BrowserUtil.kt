package com.kvxd.wellblechhack.render

import com.cinemamod.mcef.MCEF

object BrowserUtil {

    private const val INITIAL_URL = "https://example.org"

    fun generate(url: String? = null): MCEFScreen {
        val browser = MCEF.createBrowser(url ?: INITIAL_URL, true)
        val screen = MCEFScreen(browser)
        return screen
    }

}