plugins {
    id("shadesphere.android.library")
    id("shadesphere.koin")
    id("com.google.devtools.ksp")
    id("shadesphere.kotlin.serialization")
}

android {
    namespace = "dev.konathankoester.shade_sphere.core.database"
}

dependencies {

    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.11.0")
    implementation("androidx.room:room-runtime:2.8.4")
    implementation("androidx.room:room-paging:2.8.4")
    implementation("androidx.sqlite:sqlite-bundled:2.7.0")
    ksp("androidx.room:room-compiler:2.8.4")

    testImplementation("org.robolectric:robolectric:4.16.1")
    testImplementation("androidx.test:core:1.7.0")

    implementation("com.google.dagger:hilt-android:2.60.1")
    ksp("com.google.dagger:hilt-android-compiler:2.60.1")
    ksp("com.google.dagger:hilt-compiler:2.60.1")

    androidTestImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.11.0")
    androidTestImplementation("com.google.truth:truth:1.4.5")
}
