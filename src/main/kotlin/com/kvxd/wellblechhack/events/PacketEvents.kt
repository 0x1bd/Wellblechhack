package com.kvxd.wellblechhack.events

import com.kvxd.wellblechhack.CancellableEvent
import net.minecraft.network.packet.Packet

class PacketSendEvent(val packet: Packet<*>): CancellableEvent
class PacketReceivedEvent(val packet: Packet<*>): CancellableEvent