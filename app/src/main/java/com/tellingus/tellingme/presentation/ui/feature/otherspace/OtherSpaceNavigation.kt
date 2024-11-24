package com.tellingus.tellingme.presentation.ui.feature.otherspace

import android.util.Log
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.tellingus.tellingme.presentation.ui.common.navigation.OtherSpaceDestinations
import com.tellingus.tellingme.presentation.ui.feature.otherspace.detail.OtherSpaceDetailScreen
import com.tellingus.tellingme.presentation.ui.feature.otherspace.list.OtherSpaceListScreen

fun NavGraphBuilder.otherSpaceGraph(
    navController: NavController
) {
    navigation(
        route = OtherSpaceDestinations.ROUTE,
        startDestination = OtherSpaceDestinations.OTHER_SPACE
    ) {
        composable(route = OtherSpaceDestinations.OTHER_SPACE) {
            OtherSpaceScreen(navController = navController)
        }
        composable(
            route = "${OtherSpaceDestinations.OTHER_SPACE}/list/{$KEY_ID}",
            arguments = listOf(
                navArgument(KEY_ID) {
                    type = NavType.StringType
                }
            )
        ) { navBackStackEntry ->
            val date = navBackStackEntry.arguments?.getString(KEY_ID) ?: "Unknown"
            OtherSpaceListScreen(navController = navController, date = date)
        }
        composable(route = "${OtherSpaceDestinations.OTHER_SPACE}/detail/{$KEY_ID}?date={date}",
            arguments = listOf(
                navArgument(KEY_ID) {
                    type = NavType.IntType
                },
                navArgument("date") {
                    type = NavType.StringType
                    nullable = true // query-parameter는 nullable을 명시해야함
                    defaultValue = null
                }
            )
        ) { navBackStackEntry ->
            val answerId = navBackStackEntry.arguments?.getInt(KEY_ID)
            val date = navBackStackEntry.arguments?.getString("date")
            OtherSpaceDetailScreen(navController = navController, answerId = answerId, date = date)
        }
    }
}

const val KEY_ID = "key-id"
