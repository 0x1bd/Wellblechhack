package com.kvxd.wellblechhack.web

import com.kvxd.wellblechhack.mc

data class WebViewDimension(
    val x: Int,
    val y: Int,
    val width: Int,
    val height: Int,
    val fullScreen: Boolean = false
) {

    fun x(x: Double) = x - this.x
    fun y(y: Double) = y - this.y

    companion object {
        val FULLSCREEN
            get() = WebViewDimension(0, 0, mc.window.width, mc.window.height, true)
    }
}
