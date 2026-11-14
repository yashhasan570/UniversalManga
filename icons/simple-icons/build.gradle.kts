plugins {
    alias(universalmangax.plugins.android.library)
    alias(universalmangax.plugins.compose)

    alias(universalmangax.plugins.spotless)

    alias(libs.plugins.valkyrie)
}

android {
    namespace = "universalmanga.icons.simpleicons"
}

spotless {
    format("svg") {
        target("src/**/*.svg")
        trimTrailingWhitespace()
        endWithNewline()
    }
}

valkyrie {
    packageName = "universalmanga.icons.simpleicons"
    generateAtSync = true

    imageVector {
        suppressUnusedReceiverWarning = true
    }

    iconPack {
        name = "SimpleIcons"
        targetSourceSet = "main"
    }
}

dependencies {
    api(libs.androidx.compose.ui)
}
