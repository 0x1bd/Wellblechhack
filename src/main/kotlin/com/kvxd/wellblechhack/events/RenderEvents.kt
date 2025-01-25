package com.kvxd.wellblechhack.events

import com.kvxd.eventbus.Event
import net.minecraft.client.gui.DrawContext

class RenderEvent(val state: State): Event
class OverlayRenderEvent(val context: DrawContext, val state: State): Event