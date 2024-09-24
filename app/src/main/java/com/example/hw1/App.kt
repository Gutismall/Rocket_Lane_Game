package com.example.hw1

import android.app.Application
import com.example.hw1.utilities.DataManger
import com.example.hw1.utilities.ImageLoader
import com.example.hw1.utilities.SharedPreferences

class App: Application() {
    override fun onCreate() {
        super.onCreate()
        SharedPreferences.init(this)
        DataManger.init(this)
        ImageLoader.init(this)
    }
}
