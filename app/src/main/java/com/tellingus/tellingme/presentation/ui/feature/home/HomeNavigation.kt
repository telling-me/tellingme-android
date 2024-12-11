package com.tellingus.tellingme.presentation.ui.feature.home

import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.tellingus.tellingme.presentation.ui.common.navigation.HomeDestinations
import com.tellingus.tellingme.presentation.ui.feature.home.mytellerbadge.MyTellerBadgeScreen
import com.tellingus.tellingme.presentation.ui.feature.home.record.RecordScreen
import com.tellingus.tellingme.presentation.ui.feature.home.tellercard.TellerCardScreen
import com.tellingus.tellingme.presentation.ui.feature.home.tellercardtuning.TellerCardTuningScreen
import com.tellingus.tellingme.presentation.ui.feature.home.userfeedback.UserFeedbackBadScreen
import com.tellingus.tellingme.presentation.ui.feature.home.userfeedback.UserFeedbackGoodScreen
import com.tellingus.tellingme.presentation.ui.feature.home.userfeedback.UserFeedbackScreen

fun NavGraphBuilder.homeGraph(
    navController: NavController
) {
    navigation(
        route = HomeDestinations.ROUTE,
        startDestination = HomeDestinations.HOME
    ) {
        composable(route = HomeDestinations.HOME) {
            HomeScreen(navController = navController)
        }
        composable(
            route = "${HomeDestinations.RECORD}/{date}/{type}",
            arguments = listOf(
                navArgument("date") { type = NavType.StringType },
                navArgument("type") { type = NavType.StringType }
            )
            ) {
            val parentEntry = remember(it) {
                navController.getBackStackEntry(HomeDestinations.HOME)
            }

            val date = it.arguments?.getString("date")
            val type = it.arguments?.getString("type")

            RecordScreen(
                navController = navController,
                date = date!!,
                homeViewModel = hiltViewModel(parentEntry),
                type = type!!
            )
        }
        composable(route = HomeDestinations.TELLER_CARD) {
            TellerCardScreen(navController = navController)
        }
        composable(route = HomeDestinations.MY_TELLER_BADGE) {
            MyTellerBadgeScreen(navController = navController)
        }
        composable(route = HomeDestinations.TELLER_CARD_TUNING) {
            TellerCardTuningScreen(navController = navController)
        }
        composable(route = HomeDestinations.USER_FEEDBACK) {
            UserFeedbackScreen(navController = navController)
        }
        composable(route = HomeDestinations.USER_FEEDBACK_GOOD) {
            UserFeedbackGoodScreen(navController = navController)
        }
        composable(route = HomeDestinations.USER_FEEDBACK_BAD) {
            UserFeedbackBadScreen(navController = navController)
        }

    }
}