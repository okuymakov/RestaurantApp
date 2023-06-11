plugins {
    id("restaurantapp.android.library")
    id("kotlinx-serialization")
    id("kotlin-kapt")
}

android.namespace = "com.example.data"

dependencies {
    implementation(project(":core:domain"))

    implementation(libs.ktx.core)
    implementation(libs.android.appcompat)
    implementation(libs.material)

    //ktor
    implementation(libs.ktor.client)
    implementation(libs.ktor.negotiation)
    implementation(libs.ktor.serialization)
    implementation(libs.ktor.logging)
    implementation(libs.kotlin.serialization)

    //dagger
    implementation(libs.dagger)
    kapt(libs.dagger.compiler)

    //tests
    testImplementation(libs.junit)
}