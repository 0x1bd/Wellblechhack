package com.kvxd.wellblechhack.web

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
sealed class Event

@Serializable
@SerialName("SomeEvent")
data class SomeEvent(
    val message: String,
) : Event()

@Serializable
@SerialName("AnotherEvent")
data class AnotherEvent(
    val value: Int,
) : Event()