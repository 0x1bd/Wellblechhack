package com.kvxd.wellblechhack.events

import com.kvxd.eventbus.Event
import net.minecraft.network.packet.Packet

class PacketSendEvent(val packet: Packet<*>, var canceled: Boolean): Event
class PacketReceivedEvent(val packet: Packet<*>, var canceled: Boolean): Event