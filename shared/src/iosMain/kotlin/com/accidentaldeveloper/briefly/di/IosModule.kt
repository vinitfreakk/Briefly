package com.accidentaldeveloper.briefly.di

import org.koin.dsl.module

val iosModule = module {

}
actual fun startAppKoin() {
    initKoin(
        appModules = listOf(iosModule)  // also fix this — see below
    )
}