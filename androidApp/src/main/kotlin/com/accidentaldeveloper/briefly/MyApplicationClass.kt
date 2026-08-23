package com.accidentaldeveloper.briefly

import android.app.Application
import com.accidentaldeveloper.briefly.di.startAppKoin

class MyApplicationClass: Application() {
    override fun onCreate() {
        super.onCreate()
        startAppKoin()
    }
}