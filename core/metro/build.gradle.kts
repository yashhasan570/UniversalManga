plugins {
    alias(universalmangax.plugins.android.library)
    alias(universalmangax.plugins.spotless)

    alias(libs.plugins.metro)
}

android {
    namespace = "universalmanga.core.metro"
}

dependencies {
    implementation(libs.metro.runtime)
}
