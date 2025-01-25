package com.kvxd.wellblechhack.render.webview

import com.kvxd.wellblechhack.mc

data class WebViewPosition(
    var x: Int,
    var y: Int,
    var width: Int,
    var height: Int,
    var fullScreen: Boolean = false
) {

    companion object {
        val FULLSCREEN
            get() = WebViewPosition(0, 0, mc.window.framebufferWidth, mc.window.framebufferHeight, true)
    }

}