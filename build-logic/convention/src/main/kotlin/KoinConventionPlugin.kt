import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType
class KoinConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")

            dependencies {
                add("implementation", platform(libs.findLibrary("koin.bom").get()))
                add("implementation", libs.findLibrary("koin.core").get())
            }

            pluginManager.withPlugin("com.android.base") {
                dependencies {
                    add("implementation", libs.findLibrary("koin.android").get())
                }
            }

            pluginManager.withPlugin("org.jetbrains.kotlin.plugin.compose") {
                dependencies {
                    add("implementation", libs.findLibrary("koin.compose").get())
                    add("implementation", libs.findLibrary("koin.compose.viewmodel").get())
                }
            }
        }
    }
}
