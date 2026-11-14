import com.android.build.api.dsl.KotlinMultiplatformAndroidLibraryTarget
import universalmanga.gradle.configurations.configureKotlin
import universalmanga.gradle.extensions.alias
import universalmanga.gradle.extensions.configureTest
import universalmanga.gradle.extensions.coreLibraryDesugaring
import universalmanga.gradle.extensions.libs
import universalmanga.gradle.extensions.universalmangax
import universalmanga.gradle.extensions.plugins
import universalmanga.gradle.extensions.release
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

@Suppress("UNUSED")
class PluginKotlinMultiplatform : Plugin<Project> {
    override fun apply(target: Project): Unit = with(target) {
        plugins {
            alias(libs.plugins.android.kmp.library)
            alias(libs.plugins.kotlin.multiplatform)
        }

        configureKotlin()
        configureTest()

        kotlin {
            @OptIn(ExperimentalKotlinGradlePluginApi::class)
            applyDefaultHierarchyTemplate()

            android {
                minSdk = universalmangax.versions.android.sdk.min.get().toInt()
                compileSdk {
                    version = release(universalmangax.versions.android.sdk.compile)
                }
                enableCoreLibraryDesugaring = true
            }
        }

        dependencies {
            coreLibraryDesugaring(libs.android.desugar)
        }
    }
}

private fun Project.kotlin(block: KotlinMultiplatformExtension.() -> Unit) {
    extensions.configure(block)
}

private fun KotlinMultiplatformExtension.android(block: KotlinMultiplatformAndroidLibraryTarget.() -> Unit) {
    targets.withType<KotlinMultiplatformAndroidLibraryTarget>().configureEach(block)
}
