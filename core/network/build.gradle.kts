import java.io.FileInputStream
import java.util.Properties

plugins {
    id("shadesphere.android.library")
    id("shadesphere.koin")
    id("shadesphere.kotlin.serialization")
}

val localProperties = Properties().apply {
    val localPropertiesFile = rootProject.file("local.properties")
    if (localPropertiesFile.exists()) {
        load(FileInputStream(localPropertiesFile))
    }
}

android {
    namespace = "dev.konathankoester.shade_sphere.core.network"

    defaultConfig {
        buildConfigField(
            "String",
            "GEMINI_API_KEY",
            "\"${localProperties.getProperty("GEMINI_API_KEY", "")}\""
        )
    }

    buildFeatures {
        buildConfig = true
    }
}

dependencies {
    implementation(libs.ktor.client.core)
    implementation(libs.ktor.client.cio)
}