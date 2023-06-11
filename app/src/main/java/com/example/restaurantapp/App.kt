package com.example.restaurantapp

import android.app.Application
import com.example.restaurantapp.di.AppComponent

class App : Application() {
    val appComponent: AppComponent = AppComponent.init()
}