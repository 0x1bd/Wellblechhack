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

@Serializable
@SerialName("ClickGuiInfo")
data class ClickGuiInfoEvent(
    val data: List<Category>,
) : Event()

@Serializable
data class Category(val name: String, val modules: List<Module>)

@Serializable
data class Module(val name: String, val description: String, val enabled: Boolean, val settings: List<Setting>)

@Serializable
data class Setting(val type: String, val label: String)