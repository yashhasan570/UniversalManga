plugins {
    alias(universalmangax.plugins.android.library)
    alias(universalmangax.plugins.spotless)

    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "universalmanga.core.archive"
}

dependencies {
    implementation(libs.jsoup)
    implementation(libs.archive)
    implementation(libs.unifile)
}
