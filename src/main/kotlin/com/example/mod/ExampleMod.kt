package com.example.mod

import com.example.exampleMod.generated.ExampleModModules
import com.example.mod.config.Config
import com.teamresourceful.resourcefulconfig.api.loader.Configurator
import me.owdding.ktmodules.Module
import net.fabricmc.api.ModInitializer
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import tech.thatgravyboat.skyblockapi.api.SkyBlockAPI

@Module
object ExampleMod : ModInitializer, Logger by LoggerFactory.getLogger("ExampleMod") {
    private val configurator = Configurator("examplemod")

    override fun onInitialize() {
        Config.register(configurator)

        ExampleModModules.init { SkyBlockAPI.eventBus.register(it) }

        info("Hello World!")
    }
}