package com.accidentaldeveloper.briefly.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.ui.NavDisplay
import com.accidentaldeveloper.briefly.ui.bookmark.BookMarkScreen
import com.accidentaldeveloper.briefly.ui.home.HomeScreen
import com.accidentaldeveloper.briefly.ui.home.HomeScreenViewModel
import com.accidentaldeveloper.briefly.ui.search.SearchScreen
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun AppNavigation() {

    val backstack = remember {
        mutableStateListOf<NavDestination>(
            NavDestination.HomeScreen
        )
    }

    val currentDestination = backstack.last()

    Box(
        modifier = Modifier.fillMaxSize().background(Color.Black)
    ) {

        // Screens
        NavDisplay(
            modifier = Modifier.fillMaxSize().background(Color.Black),
            backStack = backstack,
            entryProvider = { key ->

                when (key) {

                    NavDestination.HomeScreen -> {
                        NavEntry(key) {
                            val homeScreenViewModel: HomeScreenViewModel = koinViewModel()
                            HomeScreen(homeScreenViewModel)
                        }
                    }

                    NavDestination.SearchScreen -> {
                        NavEntry(key) {
                            SearchScreen()
                        }
                    }

                    NavDestination.BookMarkScreen -> {
                        NavEntry(key) {
                            BookMarkScreen()
                        }
                    }
                }
            }
        )

        // Floating Bottom Bar
        AppBottomBar(
            currentDestination = currentDestination,
            onDestinationClicked = { destination ->

                if (currentDestination != destination) {
                    backstack.clear()
                    backstack.add(destination)
                }
            },
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 48.dp)
        )
    }
}


@Composable
fun AppBottomBar(
    currentDestination: NavDestination,
    onDestinationClicked: (NavDestination) -> Unit,
    modifier: Modifier = Modifier
) {

    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {

        Row(
            modifier = Modifier
                .background(
                    color = Color(0xFF222222),
                    shape = RoundedCornerShape(50.dp)
                )
                .padding(6.dp),

            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            BottomBarDestination.entries.forEach { destination ->

                IconButton(
                    modifier = Modifier
                        .size(56.dp)
                        .clip(
                        CircleShape
                    ).background(color = if (currentDestination == destination.navDestination) Color.White else Color(0xFF373636)),
                    onClick = {
                        onDestinationClicked(
                            destination.navDestination
                        )
                    }
                ) {

                    Icon(
                        painter = painterResource(
                            if (currentDestination == destination.navDestination) {
                                destination.selectedIcon
                            } else {
                                destination.unSelectedIcon
                            }
                        ),
                        contentDescription = destination.name,
                        tint = Color.Unspecified
                    )
                }
            }
        }
    }
}