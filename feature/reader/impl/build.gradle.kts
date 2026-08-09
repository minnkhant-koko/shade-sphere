plugins {
    id("shadesphere.android.feature")
}

android {
    namespace = "dev.konathankoester.shade_sphere.feature.reader"
}

dependencies {
    implementation(projects.core.design)
}
