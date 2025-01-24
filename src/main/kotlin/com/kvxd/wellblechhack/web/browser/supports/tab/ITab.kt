package com.kvxd.wellblechhack.web.browser.supports.tab

import net.minecraft.util.Identifier

interface ITab {

    var position: TabPosition

    var drawn: Boolean
    var preferOnTop: Boolean

    fun forceReload()
    fun reload()
    fun goForward()
    fun goBack()
    fun loadUrl(url: String)
    fun getUrl(): String
    fun closeTab()
    fun getTexture(): Identifier
    fun resize(width: Int, height: Int)

    fun preferOnTop(): ITab {
        preferOnTop = true
        return this
    }


}
