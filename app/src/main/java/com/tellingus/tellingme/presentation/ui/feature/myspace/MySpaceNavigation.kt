package com.tellingus.tellingme.presentation.ui.feature.myspace

import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.tellingus.tellingme.presentation.ui.common.navigation.HomeDestinations
import com.tellingus.tellingme.presentation.ui.common.navigation.MyPageDestinations
import com.tellingus.tellingme.presentation.ui.common.navigation.MySpaceDestinations
import com.tellingus.tellingme.presentation.ui.feature.home.record.RecordScreen

fun NavGraphBuilder.mySpaceGraph(
    navController: NavController
) {
    navigation(
        route = MySpaceDestinations.ROUTE,
        startDestination = MySpaceDestinations.MY_SPACE
    ) {
        composable(route = MySpaceDestinations.MY_SPACE) {
            MySpaceScreen(navController = navController)
        }

        composable(
            route = "${MySpaceDestinations.RECORD}/{date}/{type}",
            arguments = listOf(
                navArgument("date") { type = NavType.StringType },
                navArgument("type") { type = NavType.StringType },
            )
        ) {
            val date = it.arguments?.getString("date")
            val type = it.arguments?.getString("type")

            RecordScreen(
                navController = navController,
                date = date!!,
                type = type!!
            )
        }

        composable(
            route = "${MySpaceDestinations.CARD_DETAIL}/{index}",
            arguments = listOf(
                navArgument("index") { type = NavType.IntType },
            )
        ) {
            val index = it.arguments?.getInt("index")

            val parentEntry = remember(it) {
                navController.getBackStackEntry(MySpaceDestinations.MY_SPACE)
            }

            MySpaceDetailScreen(
                navController = navController,
                viewModel = hiltViewModel(parentEntry),
                index = index!!,
            )
        }
    }
}