import com.android.build.api.dsl.ApplicationDefaultConfig
import com.android.build.api.dsl.CommonExtension
import com.android.build.api.dsl.CompileOptions
import com.android.build.api.dsl.DefaultConfig
import universalmanga.gradle.configurations.configureKotlin
import universalmanga.gradle.extensions.android
import universalmanga.gradle.extensions.configureTest
import universalmanga.gradle.extensions.coreLibraryDesugaring
import universalmanga.gradle.extensions.libs
import universalmanga.gradle.extensions.universalmangax
import universalmanga.gradle.extensions.release
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

@Suppress("UNUSED")
class PluginAndroidBase : Plugin<Project> {
    override fun apply(target: Project): Unit = with(target) {
        configureKotlin()
        configureTest()

        android {
            defaultConfig {
                minSdk = universalmangax.versions.android.sdk.min.get().toInt()
                if (this is ApplicationDefaultConfig) {
                    targetSdk = universalmangax.versions.android.sdk.target.get().toInt()
                }

                ndkVersion = universalmangax.versions.android.ndk.get()
            }

            compileSdk {
                version = release(universalmangax.versions.android.sdk.compile)
            }

            compileOptions {
                isCoreLibraryDesugaringEnabled = true
            }
        }

        dependencies {
            coreLibraryDesugaring(libs.android.desugar)
        }
    }
}

private fun CommonExtension.defaultConfig(block: DefaultConfig.() -> Unit) {
    defaultConfig.apply(block)
}

private fun CommonExtension.compileOptions(block: CompileOptions.() -> Unit) {
    compileOptions.apply(block)
}
