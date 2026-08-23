package com.accidentaldeveloper.briefly

import android.app.Application
import com.accidentaldeveloper.briefly.di.initKoin

class MyApplicationClass: Application() {
    override fun onCreate() {
        super.onCreate()
        initKoin()
    }
}