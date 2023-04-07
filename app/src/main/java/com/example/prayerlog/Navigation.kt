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
            // Describing what are my arguments...
            route = Screen.DashboardScreen.route
                    + "/{fajrAmount}/{fajrAmountMax}/{dohrAmount}/{dohrAmountMax}/{asrAmount}/" +
                    "{asrAmountMax}/{maghrebAmount}/{maghrebAmountMax}/{ishaAmount}/{ishaAmountMax}",
            arguments = listOf(
                navArgument("fajrAmount") {
                    type = NavType.IntType
                    defaultValue = 0
                    nullable = false
                },
                navArgument("fajrAmountMax") {
                    type = NavType.IntType
                    defaultValue = 0
                    nullable = false
                },
                navArgument("dohrAmount") {
                    type = NavType.IntType
                    defaultValue = 0
                    nullable = false
                },
                navArgument("dohrAmountMax") {
                    type = NavType.IntType
                    defaultValue = 0
                    nullable = false
                },
                navArgument("asrAmount") {
                    type = NavType.IntType
                    defaultValue = 0
                    nullable = false
                },
                navArgument("asrAmountMax") {
                    type = NavType.IntType
                    defaultValue = 0
                    nullable = false
                },
                navArgument("maghrebAmount") {
                    type = NavType.IntType
                    defaultValue = 0
                    nullable = false
                },
                navArgument("maghrebAmountMax") {
                    type = NavType.IntType
                    defaultValue = 0
                    nullable = false
                },
                navArgument("ishaAmount") {
                    type = NavType.IntType
                    defaultValue = 0
                    nullable = false
                },
                navArgument("ishaAmountMax") {
                    type = NavType.IntType
                    defaultValue = 0
                    nullable = false
                }
            )
        ) {entry ->
            // ... and how I get them back
            DashboardScreen(
                fajr = Prayer(
                    R.string.fajr_prayer_text,
                    entry.arguments!!.getInt("fajrAmountMax"),
                    entry.arguments!!.getInt("fajrAmount"),
                ),
                dohr = Prayer(
                    R.string.dohr_prayer_text,
                    entry.arguments!!.getInt("dohrAmountMax"),
                    entry.arguments!!.getInt("dohrAmount"),
                ),
                asr = Prayer(
                    R.string.asr_prayer_text,
                    entry.arguments!!.getInt("asrAmountMax"),
                    entry.arguments!!.getInt("asrAmount"),
                ),
                maghreb = Prayer(
                    R.string.maghreb_prayer_text,
                    entry.arguments!!.getInt("maghrebAmountMax"),
                    entry.arguments!!.getInt("maghrebAmount"),
                ),
                isha = Prayer(
                    R.string.isha_prayer_text,
                    entry.arguments!!.getInt("ishaAmountMax"),
                    entry.arguments!!.getInt("ishaAmount"),
                ),
            )
        }
    }
}