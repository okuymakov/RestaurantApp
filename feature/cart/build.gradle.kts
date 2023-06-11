plugins {
    id("restaurantapp.android.library")
    id("kotlin-kapt")
}

android.namespace = "com.example.cart"


dependencies {
    implementation(project(":core:ui"))
    implementation(project(":core:domain"))

    implementation(libs.ktx.core)
    implementation(libs.ktx.fragment)
    implementation(libs.android.appcompat)
    implementation(libs.material)
    implementation(libs.lifecycle.viewmodel)

    //adapterdelegates
    implementation(libs.adapterdelegates)
    implementation(libs.adapterdelegates.viewbinding)

    //dagger
    implementation(libs.dagger)
    kapt(libs.dagger.compiler)

    //tests
    testImplementation(libs.junit)
    androidTestImplementation(libs.android.junit)
    androidTestImplementation(libs.espresso.core)
}