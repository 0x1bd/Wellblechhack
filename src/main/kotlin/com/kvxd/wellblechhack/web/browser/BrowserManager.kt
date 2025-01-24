package com.kvxd.wellblechhack.web.browser

import com.kvxd.wellblechhack.Wellblechhack
import com.kvxd.wellblechhack.logger
import com.kvxd.wellblechhack.web.browser.supports.IBrowser
import com.kvxd.wellblechhack.web.browser.supports.JcefBrowser

object BrowserManager {

    /**
     * The current browser instance.
     */
    val browser: IBrowser by lazy {
        JcefBrowser()
    }

    @Suppress("unused")
    val browserDrawer = BrowserDrawer { browser }

    @Suppress("unused")
    private val browserInput = BrowserInput { browser }

    /**
     * Initializes the browser.
     */
    fun initBrowser() {
        // Be aware, this will block the execution of the client until the browser dependencies are available.
        browser.makeDependenciesAvailable {
            runCatching {
                // Initialize the browser backend
                browser.initBrowserBackend()

            }.onFailure(Wellblechhack::fatal)
        }
    }

    /**
     * Shuts down the browser.
     */
    fun shutdownBrowser() = runCatching {
        browser.shutdownBrowserBackend()
    }.onFailure {
        logger.error("Failed to shutdown browser.", it)
    }.onSuccess {
        logger.info("Successfully shutdown browser.")
    }

}
