package com.example.mod.feature

import me.owdding.ktmodules.Module
import tech.thatgravyboat.skyblockapi.api.events.base.Subscription
import tech.thatgravyboat.skyblockapi.api.events.chat.ChatReceivedEvent

@Module
object SomeFeature {

    @Subscription
    fun onChat(event: ChatReceivedEvent.Post) {
        println("Chat message received: ${event.text}")
    }

}