package com.tellingus.tellingme.presentation.ui.feature.mypage

import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.tellingus.tellingme.presentation.ui.common.navigation.AuthDestinations
import com.tellingus.tellingme.presentation.ui.common.navigation.MyPageDestinations
import com.tellingus.tellingme.presentation.ui.feature.mypage.abouttellingme.AboutTellingMe
import com.tellingus.tellingme.presentation.ui.feature.mypage.alarm.AlarmScreen
import com.tellingus.tellingme.presentation.ui.feature.mypage.myinfoedit.MyInfoEditScreen
import com.tellingus.tellingme.presentation.ui.feature.mypage.mylog.MyLogScreen
import com.tellingus.tellingme.presentation.ui.feature.mypage.setting.SettingScreen
import com.tellingus.tellingme.presentation.ui.feature.mypage.withdraw.WithDrawScreen

fun NavGraphBuilder.myPageGraph(
    navController: NavController
) {
    navigation(
        route = MyPageDestinations.ROUTE,
        startDestination = MyPageDestinations.MY_PAGE
    ) {
        composable(route = MyPageDestinations.MY_PAGE) {
            MyPageScreen(navController = navController)
        }
        composable(route = MyPageDestinations.ALARM) {
            AlarmScreen(navController)
        }
        composable(route = MyPageDestinations.MY_LOG) {
            MyLogScreen(navController = navController)
        }
        composable(route = MyPageDestinations.SETTING) {
            SettingScreen(navController = navController)
        }
        composable(route = MyPageDestinations.MY_INFO_EDIT) {
            val parentEntry = remember(it) {
                navController.getBackStackEntry(MyPageDestinations.MY_PAGE)
            }
            MyInfoEditScreen(
                navController = navController,
                viewModel = hiltViewModel(parentEntry)
            )
        }
        composable(route = MyPageDestinations.WITH_DRAW) {
            val parentEntry = remember(it) {
                navController.getBackStackEntry(MyPageDestinations.MY_PAGE)
            }
            WithDrawScreen(navController = navController, hiltViewModel(parentEntry))
        }
        composable(route = MyPageDestinations.ABOUT_TELLING_ME) {
            AboutTellingMe(navController = navController)
        }
    }
}