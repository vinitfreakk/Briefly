package com.accidentaldeveloper.briefly.di

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.accidentaldeveloper.briefly.datastore.createIosDataStore
import org.koin.dsl.module

val iosModule = module {
    single<DataStore<Preferences>> {
        createIosDataStore()
    }
}
actual fun startAppKoin() {
    initKoin(
        appModules = listOf(iosModule)  // also fix this — see below
    )
}