package com.example.mod

import com.example.mod.config.Config
import com.teamresourceful.resourcefulconfig.api.loader.Configurator
import net.fabricmc.api.ModInitializer
import tech.thatgravyboat.skyblockapi.api.SkyBlockAPI

object ExampleMod : ModInitializer {
    private val configurator = Configurator("sbpv")

    override fun onInitialize() {
        Config.register(configurator)
        SkyBlockAPI.eventBus.register(this)

        println("Hello, World!")
    }
}