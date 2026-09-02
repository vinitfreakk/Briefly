package com.accidentaldeveloper.briefly.di

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.accidentaldeveloper.briefly.database.getDatabaseBuilderForIos
import com.accidentaldeveloper.briefly.datastore.createIosDataStore
import com.accidentaldeveloper.briefly.platform.IosShare
import com.accidentaldeveloper.briefly.platform.Share
import org.koin.dsl.module

val iosModule = module {
    single<DataStore<Preferences>> {
        createIosDataStore()
    }

    single {
        getDatabaseBuilderForIos()
    }

    single<Share> {
        IosShare()
    }
}
actual fun startAppKoin() {
    initKoin(
        appModules = listOf(iosModule)  // also fix this — see below
    )
}