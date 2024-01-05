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
                    + "/{sobhAmountPrayed}/{sobhAmountToPray}/{dohrAmountPrayed}/{dohrAmountToPray}/{asrAmountPrayed}/" +
                    "{asrAmountToPray}/{maghrebAmountPrayed}/{maghrebAmountToPray}/{ishaAmountPrayed}/{ishaAmountToPray}",
            arguments = listOf(
                navArgument("sobhAmountPrayed") {
                    type = NavType.IntType
                    defaultValue = 0
                    nullable = false
                },
                navArgument("sobhAmountToPray") {
                    type = NavType.IntType
                    defaultValue = 0
                    nullable = false
                },
                navArgument("dohrAmountPrayed") {
                    type = NavType.IntType
                    defaultValue = 0
                    nullable = false
                },
                navArgument("dohrAmountToPray") {
                    type = NavType.IntType
                    defaultValue = 0
                    nullable = false
                },
                navArgument("asrAmountPrayed") {
                    type = NavType.IntType
                    defaultValue = 0
                    nullable = false
                },
                navArgument("asrAmountToPray") {
                    type = NavType.IntType
                    defaultValue = 0
                    nullable = false
                },
                navArgument("maghrebAmountPrayed") {
                    type = NavType.IntType
                    defaultValue = 0
                    nullable = false
                },
                navArgument("maghrebAmountToPray") {
                    type = NavType.IntType
                    defaultValue = 0
                    nullable = false
                },
                navArgument("ishaAmountPrayed") {
                    type = NavType.IntType
                    defaultValue = 0
                    nullable = false
                },
                navArgument("ishaAmountToPray") {
                    type = NavType.IntType
                    defaultValue = 0
                    nullable = false
                }
            )
        ) {entry ->
            // ... and how I get them back
            DashboardScreen(
                sobh = Prayer(
                    nameId = R.string.sobh,
                    amountToPray = entry.arguments!!.getInt("sobhAmountToPray"),
                    amountPrayed = entry.arguments!!.getInt("sobhAmountPrayed"),
                ),
                dohr = Prayer(
                    nameId = R.string.dohr,
                    amountToPray = entry.arguments!!.getInt("dohrAmountToPray"),
                    amountPrayed = entry.arguments!!.getInt("dohrAmountPrayed"),
                ),
                asr = Prayer(
                    nameId = R.string.asr,
                    amountToPray = entry.arguments!!.getInt("asrAmountToPray"),
                    amountPrayed = entry.arguments!!.getInt("asrAmountPrayed"),
                ),
                maghreb = Prayer(
                    nameId = R.string.maghreb,
                    amountToPray = entry.arguments!!.getInt("maghrebAmountToPray"),
                    amountPrayed = entry.arguments!!.getInt("maghrebAmountPrayed"),
                ),
                isha = Prayer(
                    nameId = R.string.icha,
                    amountToPray = entry.arguments!!.getInt("ishaAmountToPray"),
                    amountPrayed = entry.arguments!!.getInt("ishaAmountPrayed"),
                ),
            )
        }
    }
}