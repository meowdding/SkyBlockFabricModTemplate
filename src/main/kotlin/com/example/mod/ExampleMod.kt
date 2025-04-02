package com.example.mod

import net.fabricmc.api.ModInitializer
import tech.thatgravyboat.skyblockapi.api.SkyBlockAPI

object ExampleMod : ModInitializer {
    override fun onInitialize() {
        SkyBlockAPI.eventBus.register(this)

        println("Hello, World!")
    }
}