package com.kvxd.wellblechhack.web.browser.supports.tab

interface InputAware {

    val takesInput: () -> Boolean

    fun mouseClicked(mouseX: Double, mouseY: Double, mouseButton: Int)

    fun mouseReleased(mouseX: Double, mouseY: Double, mouseButton: Int)

    fun mouseMoved(mouseX: Double, mouseY: Double)

    fun mouseScrolled(mouseX: Double, mouseY: Double, delta: Double)

    fun keyPressed(keyCode: Int, scanCode: Int, modifiers: Int)

    fun keyReleased(keyCode: Int, scanCode: Int, modifiers: Int)

    fun charTyped(char: Char, modifiers: Int)

}
