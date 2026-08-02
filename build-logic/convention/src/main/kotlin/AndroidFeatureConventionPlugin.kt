import org.gradle.api.Plugin
import org.gradle.api.Project

abstract class AndroidFeatureConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            pluginManager.apply("shadesphere.android.library.compose")
            pluginManager.apply("shadesphere.koin")
        }
    }
}
