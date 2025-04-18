package com.example.mod.config

import com.example.mod.config.categories.ExampleCategory
import com.teamresourceful.resourcefulconfig.api.types.info.ResourcefulConfigLink
import com.teamresourceful.resourcefulconfig.api.types.options.TranslatableValue
import com.teamresourceful.resourcefulconfigkt.api.ConfigKt

object Config : ConfigKt("example/config") {
    override val name get() = TranslatableValue("Example Mod")
    override val description get() = TranslatableValue("some description")
    override val links: Array<ResourcefulConfigLink>
        get() = arrayOf(
            ResourcefulConfigLink.create(
                "<link>>",
                "discord",
                TranslatableValue("Discord"),
            ),
        )

    init {
        category(ExampleCategory)
    }

    var toggle by boolean(false) {
        name = Translated("translation.path")
        description = Translated("translation.path.desc")
    }

}