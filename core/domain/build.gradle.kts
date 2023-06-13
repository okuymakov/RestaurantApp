plugins {
    id("restaurantapp.android.library")
    id("kotlin-kapt")
    id("kotlin-parcelize")
}
android.namespace = "com.example.domain"

dependencies {
    //kotlin
    implementation(libs.kotlin.coroutines)
    //dagger
    implementation(libs.dagger)
    kapt(libs.dagger.compiler)
}