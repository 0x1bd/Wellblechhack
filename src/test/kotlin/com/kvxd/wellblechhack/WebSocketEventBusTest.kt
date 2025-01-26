package com.kvxd.wellblechhack

import com.kvxd.wellblechhack.web.AnotherEvent
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

        var i = 0

        while (true) {
            WebSocketEventBus.sendEvent(AnotherEvent(value = i))
            i++
            Thread.sleep(50)
        }
    }
}