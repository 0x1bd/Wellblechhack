package com.kvxd.wellblechhack.events

import com.kvxd.eventbus.Event
import net.minecraft.client.gui.DrawContext

class GameRenderEvent: Event
class ScreenRenderEvent(val context: DrawContext): Event
class OverlayRenderEvent(val context: DrawContext, val tickDelta: Float) : Event