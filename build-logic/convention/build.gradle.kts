plugins {
    `kotlin-dsl`
}

group = "com.shadesphere.buildlogic"

dependencies {
    compileOnly(libs.android.gradlePlugin)
    compileOnly(libs.kotlin.gradlePlugin)
}

gradlePlugin {
    plugins {
        register("koinConvention") {
            id = "shadesphere.koin"
            implementationClass = "KoinConventionPlugin"
        }
        register("androidLibrary") {
            id = "shadesphere.android.library"
            implementationClass = "AndroidLibraryConventionPlugin"
        }
        register("androidLibraryCompose") {
            id = "shadesphere.android.library.compose"
            implementationClass = "AndroidLibraryComposeConventionPlugin"
        }
        register("androidFeature") {
            id = "shadesphere.android.feature"
            implementationClass = "AndroidFeatureConventionPlugin"
        }
        register("kotlinJvm") {
            id = "shadesphere.kotlin.jvm"
            implementationClass = "KotlinJvmConventionPlugin"
        }
        register("kotlinSerialization") {
            id = "shadesphere.kotlin.serialization"
            implementationClass = "SerializationConventionPlugin"
        }
    }
}
