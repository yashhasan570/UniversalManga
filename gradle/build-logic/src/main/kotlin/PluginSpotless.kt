import com.diffplug.gradle.spotless.SpotlessExtension
import universalmanga.gradle.extensions.alias
import universalmanga.gradle.extensions.libs
import universalmanga.gradle.extensions.plugins
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure

@Suppress("UNUSED")
class PluginSpotless : Plugin<Project> {
    override fun apply(target: Project): Unit = with(target) {
        plugins {
            alias(libs.plugins.spotless)
        }

        // Configuration should be synced with [/gradle/build-logic/build.gradle.kts]
        val ktlintVersion = libs.ktlint.bom.get().version
        spotless {
            kotlin {
                target("src/**/*.kt")
                ktlint(ktlintVersion)
                trimTrailingWhitespace()
                endWithNewline()
                forbidRegex(
                    "materialSymbolsRImport",
                    "(?m)^import universalmanga\\.icons\\.materialsymbols\\.R(?! as MaterialSymbolsR$)\\b.*$",
                    "Import universalmanga.icons.materialsymbols.R as MaterialSymbolsR",
                )
                forbidRegex(
                    "materialSymbolsRQualified",
                    "(?m)^(?!import ).*\\buniversalmanga\\.icons\\.materialsymbols\\.R\\.",
                    "Use the MaterialSymbolsR import alias instead of the qualified name",
                )
                forbidRegex(
                    "customMaterialSymbolsRImport",
                    "(?m)^import universalmanga\\.icons\\.custommaterialsymbols\\.R(?! as CustomMaterialSymbolsR$)\\b.*$",
                    "Import universalmanga.icons.custommaterialsymbols.R as CustomMaterialSymbolsR",
                )
                forbidRegex(
                    "customMaterialSymbolsRQualified",
                    "(?m)^(?!import ).*\\buniversalmanga\\.icons\\.custommaterialsymbols\\.R\\.",
                    "Use the CustomMaterialSymbolsR import alias instead of the qualified name",
                )
            }

            kotlinGradle {
                target("*.kts")
                ktlint(ktlintVersion)
                trimTrailingWhitespace()
                endWithNewline()
            }

            format("xml") {
                target("src/**/*.xml")
                trimTrailingWhitespace()
                endWithNewline()
            }
        }
    }
}

private fun Project.spotless(block: SpotlessExtension.() -> Unit) {
    extensions.configure(block)
}
