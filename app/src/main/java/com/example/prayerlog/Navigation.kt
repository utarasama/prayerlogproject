package com.example.prayerlog

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument

@Composable
fun Navigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Screen.MainScreen.route) {
        composable(route = Screen.MainScreen.route) {
            PrayerFormScreen(navController = navController)
        }
        composable(
            route = Screen.DashboardScreen.route
                    + "/{fajrAmount}/{dohrAmount}/{asrAmount}/{maghrebAmount}/{ishaAmount}",
            arguments = listOf(
                navArgument("fajrAmount") {
                    type = NavType.IntType
                    defaultValue = 0
                    nullable = false
                },
                navArgument("dohrAmount") {
                    type = NavType.IntType
                    defaultValue = 0
                    nullable = false
                },
                navArgument("asrAmount") {
                    type = NavType.IntType
                    defaultValue = 0
                    nullable = false
                },
                navArgument("maghrebAmount") {
                    type = NavType.IntType
                    defaultValue = 0
                    nullable = false
                },
                navArgument("ishaAmount") {
                    type = NavType.IntType
                    defaultValue = 0
                    nullable = false
                }
            )
        ) {entry ->
            DashboardScreen(
                fajrAmount = entry.arguments!!.getInt("fajrAmount"),
                dohrAmount = entry.arguments!!.getInt("dohrAmount"),
                asrAmount = entry.arguments!!.getInt("asrAmount"),
                maghrebAmount = entry.arguments!!.getInt("maghrebAmount"),
                ishaAmount = entry.arguments!!.getInt("ishaAmount"),
            )
        }
    }
}