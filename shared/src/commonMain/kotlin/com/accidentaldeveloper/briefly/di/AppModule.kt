package com.accidentaldeveloper.briefly.di

import com.accidentaldeveloper.briefly.api.NewsApi
import com.accidentaldeveloper.briefly.repository.NewsApiRepository
import com.accidentaldeveloper.briefly.repository.NewsApiRepositoryImpl
import com.accidentaldeveloper.briefly.ui.home.HomeScreenViewModel
import com.accidentaldeveloper.briefly.ui.search.SearchViewModel
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    single<NewsApi> {
        NewsApi()
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
}

fun initKoin(appModules: List<Module> = emptyList()){
    startKoin {
        modules(appModule+appModules)
    }
}

expect fun startAppKoin()