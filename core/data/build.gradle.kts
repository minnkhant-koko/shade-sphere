plugins {
    id("shadesphere.android.library")
    id("shadesphere.koin")
    id("shadesphere.kotlin.serialization")
}

android {
    namespace = "dev.konathankoester.shade_sphere.core.data"
}

dependencies {
    api(projects.core.model)
    api(projects.core.aiGemini)
    api(projects.core.database)
}