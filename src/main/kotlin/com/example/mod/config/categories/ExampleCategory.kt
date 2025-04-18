package com.example.mod.config.categories

import com.teamresourceful.resourcefulconfigkt.api.CategoryKt

object ExampleCategory : CategoryKt("Category") {

    var longInput by long(0L) {
        name = Translated("translation.path")
        description = Translated("translation.path.desc")
    }

}