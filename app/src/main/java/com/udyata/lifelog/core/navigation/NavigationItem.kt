package com.udyata.lifelog.core.navigation

import com.udyata.lifelog.R


enum class NavigationItem(
    val title: String,
    val icon: Int,
    val route:String
) {
    Home(
        icon = R.drawable.baseline_home_filled_24,
        title = "Home",
        route = "home"
    ),
    Profile(
        icon = R.drawable.baseline_person_24,
        title = "Profile",
        route = "profile"
    ),
    Premium(
        icon = R.drawable.baseline_diamond_24,
        title = "Premium",
        route = "premium"
    ),
    Settings(
        icon = R.drawable.baseline_settings_24,
        title = "Settings",
        route = "settings"
    )
}