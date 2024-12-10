package com.tellingus.tellingme.presentation.ui.feature.myspace

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.tellingus.tellingme.presentation.ui.common.navigation.HomeDestinations
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
            route = "${MySpaceDestinations.RECORD}/{date}",
            arguments = listOf(
                navArgument("date") { type = NavType.StringType },
            )
        ) {
            val date = it.arguments?.getString("date")

            RecordScreen(
                navController = navController,
                date = date!!
            )
        }
    }
}