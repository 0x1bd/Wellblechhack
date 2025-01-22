package com.kvxd.wellblechhack.events

import com.kvxd.eventbus.Event
import com.kvxd.wellblechhack.module.Module

class ModuleEnableEvent(val module: Module): Event
class ModuleDisableEvent(val module: Module): Event