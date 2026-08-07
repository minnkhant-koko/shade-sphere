pluginManagement {
    includeBuild("build-logic")
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}
plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "1.0.0"
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "shade-sphere"
enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")
include(":app")
include(":feature:news:api")
include(":feature:news:impl")
include(":feature:practice:api")
include(":feature:practice:impl")
include(":feature:reader:api")
include(":feature:reader:impl")
include(":feature:words:api")
include(":feature:words:impl")
include(":core:data")
include(":core:database")
include(":core:network")
include(":core:model")
include(":core:ai-gemini")
