package com.accidentaldeveloper.briefly.di

import com.accidentaldeveloper.briefly.api.NewsApi
import com.accidentaldeveloper.briefly.datastore.DataStoreManager
import com.accidentaldeveloper.briefly.network.BrieflyClient
import com.accidentaldeveloper.briefly.repository.NewsApiRepository
import com.accidentaldeveloper.briefly.repository.NewsApiRepositoryImpl
import com.accidentaldeveloper.briefly.repository.SettingsRepository
import com.accidentaldeveloper.briefly.repository.SettingsRepositoryImpl
import com.accidentaldeveloper.briefly.ui.home.HomeScreenViewModel
import com.accidentaldeveloper.briefly.ui.search.SearchViewModel
import com.accidentaldeveloper.briefly.ui.settings.SettingsViewModel
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    single<NewsApi> {
        NewsApi(get())
    }

    single {
        BrieflyClient(get())
    }

    single<NewsApiRepository> {
        NewsApiRepositoryImpl(get())
    }

    viewModel {
        HomeScreenViewModel(get())
    }

    viewModel {
        SearchViewModel(get())
    }

    single {
        DataStoreManager(get())
    }

    viewModel {
        SettingsViewModel(get())
    }

    single<SettingsRepository> {
        SettingsRepositoryImpl(get())
    }
}

fun initKoin(appModules: List<Module> = emptyList()){
    startKoin {
        modules(appModule+appModules)
    }
}

expect fun startAppKoin()