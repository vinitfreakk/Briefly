package com.accidentaldeveloper.briefly.di

import org.koin.dsl.module

val androidModule = module {

}

actual fun startAppKoin() {
    initKoin(
        appModules = listOf(androidModule)
    )
}