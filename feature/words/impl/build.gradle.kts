plugins {
    id("shadesphere.android.feature")
}

android {
    namespace = "dev.konathankoester.shade_sphere.feature.words"
}

dependencies {
    implementation(projects.core.design)
}
