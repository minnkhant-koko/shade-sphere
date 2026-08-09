plugins {
    id("shadesphere.android.feature")
}

android {
    namespace = "dev.konathankoester.shade_sphere.feature.news"
}

dependencies {
    implementation(projects.core.design)
}
