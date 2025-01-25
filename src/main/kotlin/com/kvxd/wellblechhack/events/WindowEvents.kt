package com.kvxd.wellblechhack.events

import com.kvxd.eventbus.Event
import net.minecraft.client.gui.DrawContext

class WindowChangeSizeEvent(val width: Int, val height: Int): Event
class FramebufferChangeSizeEvent(val width: Int, val height: Int): Event