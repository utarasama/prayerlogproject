package com.example.prayerlog

/**
 * Represents every screen the app handles.
 */
sealed class Screen(val route: String) {
    object MainScreen: Screen("prayer_form_screen")
    object DashboardScreen: Screen("dashboard_screen")

    /**
     * Builds a route with the specified arguments.
     * @param args The `String` args we want to pass through the navigation.
     * @return A correctly built `String` route joined by slashes.
     */
    fun withArgs(vararg args: String): String {
        return buildString {
            append(route)
            args.forEach { arg ->
                append("/$arg")
            }
        }
    }

    /**
     * Builds a route with the specified arguments.
     * @param args The `Int` args we want to pass through the navigation.
     * @return A correctly built `String` route joined by slashes.
     */
    fun withArgs(vararg args: Int): String {
        return buildString {
            append(route)
            args.forEach { arg ->
                append("/$arg")
            }
        }
    }
}
