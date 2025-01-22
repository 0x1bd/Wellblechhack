package com.kvxd.wellblechhack

import com.kvxd.eventbus.Event

interface CancellableEvent : Event {

    var cancelled: Boolean
        get() = false
        set(_) {}

    fun cancel() {
        cancelled = true
    }
}
