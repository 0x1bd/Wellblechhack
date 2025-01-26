package com.kvxd.wellblechhack

import com.kvxd.wellblechhack.web.SomeEvent
import com.kvxd.wellblechhack.web.WebSocketEventBus
import kotlin.test.Test

class WebSocketEventBusTest {

    @Test
    fun simpleTest() {
        WebSocketEventBus.start()

        WebSocketEventBus.addListener<SomeEvent> { event, _ ->
            println("Received: ${event.message}")
        }
    }
}