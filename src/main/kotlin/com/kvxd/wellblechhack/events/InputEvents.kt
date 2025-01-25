package com.kvxd.wellblechhack.events

import com.kvxd.eventbus.Event
import com.kvxd.wellblechhack.CancellableEvent

class KeyEvent(val keyCode: Int, val action: Int, val scancode: Long, val modifiers: Int): CancellableEvent
class CharTypedEvent(val codePoint: Int, val modifiers: Int): CancellableEvent
class MouseButtonEvent(val button: Int, val action: Int, val x: Double, val y: Double): CancellableEvent
class MousePositionEvent(val x: Double, val y: Double): Event