plugins {
    id("restaurantapp.android.library")
}

android.namespace = "com.example.core.ui"

dependencies {
    implementation(libs.ktx.core)
    implementation(libs.android.appcompat)
    implementation(libs.material)

    implementation(libs.ktx.fragment)
    
    //coil
    implementation(libs.coil)

    //tests
    testImplementation(libs.junit)
    androidTestImplementation(libs.android.junit)
    androidTestImplementation(libs.espresso.core)
}