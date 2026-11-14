import universalmanga.gradle.extensions.alias
import universalmanga.gradle.extensions.libs
import universalmanga.gradle.extensions.universalmangax
import universalmanga.gradle.extensions.plugins
import org.gradle.api.Plugin
import org.gradle.api.Project

@Suppress("UNUSED")
class PluginAndroidApplication : Plugin<Project> {
    override fun apply(target: Project): Unit = with(target) {
        plugins {
            alias(libs.plugins.android.application)
            alias(universalmangax.plugins.android.base)
        }
    }
}
