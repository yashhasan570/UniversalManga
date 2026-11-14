dependencyResolutionManagement {
    @Suppress("UnstableApiUsage")
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
    versionCatalogs {
        create("libs") {
            from(files("../libs.versions.toml"))
        }
        create("universalmangax") {
            from(files("../universalmanga.versions.toml"))
        }
    }
}

rootProject.name = "build-logic"
