package com.kvxd.wellblechhack.web

import net.ccbluex.liquidbounce.mcef.MCEF

object BrowserUtil {

    private const val INITIAL_URL = "https://example.org"

    fun generate(url: String? = null): MCEFScreen {
        val browser = MCEF.INSTANCE.createBrowser(url ?: INITIAL_URL, true, 60)
        val screen = MCEFScreen(browser)
        return screen
    }

}