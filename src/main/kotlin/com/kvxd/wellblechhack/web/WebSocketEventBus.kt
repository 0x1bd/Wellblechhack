package com.kvxd.wellblechhack.web

import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.routing.*
import io.ktor.server.websocket.*
import io.ktor.websocket.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic
import kotlinx.serialization.modules.subclass
import kotlin.time.Duration.Companion.seconds

/**
 * Handles events between the svelte frontend and the mod backend
 * @see Event
 */
object WebSocketEventBus {

    private val eventModule = SerializersModule {
        polymorphic(Event::class) {
            subclass(SomeEvent::class)
            subclass(AnotherEvent::class)
            subclass(ClickGuiInfoEvent::class)
        }
    }

    private val json = Json {
        serializersModule = eventModule
        classDiscriminator = "eventType"
        ignoreUnknownKeys = true
    }

    private lateinit var scope: CoroutineScope
    private val sessions = mutableSetOf<DefaultWebSocketServerSession>()
    val listeners = mutableMapOf<String, (Event, DefaultWebSocketServerSession) -> Unit>()
    private var isStarted = false

    fun start(port: Int = 8080) {
        require(!isStarted) { "EventBus is already started" }
        isStarted = true

        embeddedServer(Netty, port = port) {
            install(WebSockets) {
                pingPeriod = 15.seconds
                timeout = 15.seconds
            }

            scope = CoroutineScope(this.coroutineContext + SupervisorJob())

            addListener<SomeEvent> { _, _ ->
                sendEvent(ClickGuiInfoEvent(
                    listOf(Category(
                        "Somecategory",
                        listOf(
                            Module("Mod", "Desc", false, listOf())
                        )
                    ))
                ))
            }



            routing {
                webSocket("/ws") {
                    handleSession(this)
                }
            }
        }.start(wait = true)
    }

    fun sendEvent(event: Event, session: DefaultWebSocketServerSession? = null) {
        require(isStarted) { "EventBus must be started before sending events" }
        scope.launch {
            try {
                val jsonString = json.encodeToString(event)
                if (session != null) {
                    session.send(jsonString)
                } else {
                    sessions.forEach { it.send(jsonString) }
                }
            } catch (e: Exception) {
                println("Error sending event: ${e.message}")
            }
        }
    }

    inline fun <reified T : Event> addListener(noinline handler: (T, DefaultWebSocketServerSession) -> Unit) {
        val eventType = T::class.simpleName ?: return
        listeners[eventType] = { event, session ->
            handler(event as T, session)
        }
    }

    suspend fun handleSession(session: DefaultWebSocketServerSession) {
        sessions.add(session)
        try {
            for (frame in session.incoming) {
                if (frame is Frame.Text) {
                    try {
                        val event = json.decodeFromString<Event>(frame.readText())
                        val eventType = event::class.simpleName ?: continue
                        listeners[eventType]?.invoke(event, session)
                    } catch (e: Exception) {
                        println("Error processing message: ${e.message}")
                    }
                }
            }
        } finally {
            sessions.remove(session)
        }
    }
}