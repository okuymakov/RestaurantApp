@file:Suppress("UnstableApiUsage")

pluginManagement {
    includeBuild("build-logic")
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}
rootProject.name = "RestaurantApp"
include(
    ":app",
    ":feature:home",
    ":feature:cart",
    ":feature:category",
    ":feature:product",
    ":core:data",
    ":core:domain",
    ":core:ui"
)
