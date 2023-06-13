plugins {
    id("restaurantapp.android.application")
    id("kotlin-kapt")
    id("androidx.navigation.safeargs")
}
android.namespace = "com.example.restaurantapp"

dependencies {
    implementation(project(":feature:home"))
    implementation(project(":feature:category"))
    implementation(project(":feature:product"))
    implementation(project(":feature:cart"))
    implementation(project(":core:data"))
    implementation(project(":core:domain"))
    implementation(project(":core:ui"))

    implementation(libs.ktx.core)
    implementation(libs.android.appcompat)
    implementation(libs.material)
    implementation(libs.constraintlayout)

    //coil
    implementation(libs.coil)

    //navigation
    implementation(libs.navigation.fragment)
    implementation(libs.navigation.ui)

    //dagger
    implementation(libs.dagger)
    kapt(libs.dagger.compiler)

    //google services
    implementation(libs.gms.location)

    //tests
    testImplementation(libs.junit)
    androidTestImplementation(libs.android.junit)
    androidTestImplementation(libs.espresso.core)
}