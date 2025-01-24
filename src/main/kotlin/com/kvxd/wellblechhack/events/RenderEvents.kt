package com.kvxd.wellblechhack.events

import com.kvxd.eventbus.Event
import net.minecraft.client.gui.DrawContext

class WorldRenderEvent: Event
class ScreenRenderEvent(val context: DrawContext): Event