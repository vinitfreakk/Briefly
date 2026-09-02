package com.accidentaldeveloper.briefly.di

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.accidentaldeveloper.briefly.database.getDatabaseBuilderForIos
import com.accidentaldeveloper.briefly.datastore.createIosDataStore
import com.accidentaldeveloper.briefly.platform.IosShareManager
import com.accidentaldeveloper.briefly.platform.IosWebViewManager
import com.accidentaldeveloper.briefly.platform.ShareManager
import com.accidentaldeveloper.briefly.platform.WebViewManager
import org.koin.dsl.module

val iosModule = module {
    single<DataStore<Preferences>> {
        createIosDataStore()
    }

    single {
        getDatabaseBuilderForIos()
    }

    single<ShareManager> {
        IosShareManager()
    }

    single<WebViewManager> {
        IosWebViewManager()
    }
}
actual fun startAppKoin() {
    initKoin(
        appModules = listOf(iosModule)  // also fix this — see below
    )
}