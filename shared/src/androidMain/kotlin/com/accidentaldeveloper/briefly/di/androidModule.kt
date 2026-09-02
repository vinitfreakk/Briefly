package com.accidentaldeveloper.briefly.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.accidentaldeveloper.briefly.database.getDatabaseBuilderForAndroid
import com.accidentaldeveloper.briefly.datastore.createAndroidDataStore
import com.accidentaldeveloper.briefly.platform.AndroidShareManager
import com.accidentaldeveloper.briefly.platform.AndroidWebViewManager
import com.accidentaldeveloper.briefly.platform.ShareManager
import com.accidentaldeveloper.briefly.platform.WebViewManager
import org.koin.dsl.module


lateinit var appContext: Context

val androidModule = module {
    single<Context> { appContext }
    single<DataStore<Preferences>> {
        createAndroidDataStore(get())
    }
    single {
        getDatabaseBuilderForAndroid(get())
    }

    single<ShareManager> {
        AndroidShareManager(get())
    }

    single<WebViewManager> {
        AndroidWebViewManager(get())
    }
}

actual fun startAppKoin() {
    initKoin(
        appModules = listOf(androidModule)
    )
}