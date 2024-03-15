package com.gebeya.sebsab_mobile.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Work
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomNavItem(val route: String, val icon: ImageVector, val label: String) {
    object Home : BottomNavItem("Home", Icons.Default.Home, "Home")
    object Search : BottomNavItem("Jobs", Icons.Default.Work, "Jobs")
    object Profile : BottomNavItem("Profile", Icons.Default.Person, "Profile")
}
