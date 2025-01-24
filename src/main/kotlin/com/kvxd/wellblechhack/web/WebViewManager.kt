package com.kvxd.wellblechhack.web

import com.kvxd.wellblechhack.mc
import net.ccbluex.liquidbounce.mcef.MCEFBrowser

object WebViewManager {

    var browser: MCEFBrowser? = null

    val input = WebViewInput { mc.currentScreen is WebView }
    val renderer = WebViewRenderer()

}
