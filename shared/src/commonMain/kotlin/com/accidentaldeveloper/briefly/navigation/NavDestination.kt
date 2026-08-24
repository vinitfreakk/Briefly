package com.accidentaldeveloper.briefly.navigation

import briefly.shared.generated.resources.Res
import briefly.shared.generated.resources.ic_bookmark_filled
import briefly.shared.generated.resources.ic_bookmark_outline
import briefly.shared.generated.resources.ic_home_filled
import briefly.shared.generated.resources.ic_home_outline
import briefly.shared.generated.resources.ic_search_outline
import briefly.shared.generated.resources.ic_search_filled
import org.jetbrains.compose.resources.DrawableResource

sealed interface NavDestination{
    data object HomeScreen: NavDestination

    data class NewsDetails(val newsDetails: NewsDetailsNavArgs): NavDestination
    data object SearchScreen: NavDestination
    data object BookMarkScreen: NavDestination
}

enum class BottomBarDestination( val navDestination: NavDestination,val selectedIcon: DrawableResource,val unSelectedIcon: DrawableResource) {
    HomeScreen(navDestination = NavDestination.HomeScreen, selectedIcon = Res.drawable.ic_home_filled,unSelectedIcon= Res.drawable.ic_home_outline),
    SearchScreen(navDestination = NavDestination.SearchScreen, selectedIcon =  Res.drawable.ic_search_filled,unSelectedIcon = Res.drawable.ic_search_outline),
    BookMarkScreen(navDestination = NavDestination.BookMarkScreen, selectedIcon =  Res.drawable.ic_bookmark_filled,unSelectedIcon = Res.drawable.ic_bookmark_outline)
}