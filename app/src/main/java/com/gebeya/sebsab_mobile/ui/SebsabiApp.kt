package com.gebeya.sebsab_mobile.ui

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Badge
import androidx.compose.material.BadgedBox
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavOptions
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.gebeya.sebsab_mobile.ui.components.BottomNavItem
import com.gebeya.sebsab_mobile.ui.theme.poppinsFamily
import com.gebeya.sebsab_mobile.viewmodel.SignUpPageViewModel

@SuppressLint("SuspiciousIndentation")
@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
 fun SebsabiApp(

){





    val signUpPageViewModel = hiltViewModel<SignUpPageViewModel>()
    val navController = rememberNavController()
    val canNavBack = remember{ mutableStateOf(false)}
    val currentScreen =  navController.currentBackStackEntryAsState().value?.destination?.route
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }
    val isDarkTheme = isSystemInDarkTheme()
    
    val items = listOf(
        BottomNavItem.Home,
        BottomNavItem.Search,
        BottomNavItem.Profile,

        )
    val textToDisplay = when (currentScreen) {
        "Detail/{id}" -> "Form Detail"
        "Answer/{id}" -> "Submit Answer"
        else -> currentScreen ?: "Sebsabi"
    }
 var isLoggedIn= signUpPageViewModel.check()


    Scaffold(
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState){ data-> Snackbar(snackbarData = data, containerColor = Color(0xFF909300), contentColor = Color.White ) }
        },
        bottomBar = {
            if(currentScreen != "Landing Page" && currentScreen!= "Sign Up" && currentScreen!= "Log In" ){
            BottomNavigation (backgroundColor = MaterialTheme.colorScheme.background, elevation = 20.dp){

                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentRoute = navBackStackEntry?.destination?.route

                items.forEach { screen ->
                    val isSelected = currentRoute == screen.route
                    BottomNavigationItem(
                        selected = isSelected,
                        label={ Text(text = screen.label, color = if(!isSelected) MaterialTheme.colorScheme.primary else  MaterialTheme.colorScheme.tertiary,fontFamily = poppinsFamily, fontWeight = FontWeight.SemiBold) },
                        onClick = { navController.navigate(screen.route){
                            popUpTo("Home") {
                                saveState = true
                            }
                            // Avoid multiple copies of the same destination when
                            // reselecting the same item
                            launchSingleTop = true
                        } },
                        icon = { Icon(tint =  if(!isSelected) MaterialTheme.colorScheme.primary else  MaterialTheme.colorScheme.tertiary,imageVector = screen.icon, contentDescription = null ) },
                        selectedContentColor = if (isDarkTheme) Color.White else Color.Black,
                        unselectedContentColor = if (isDarkTheme) Color.Gray else Color.DarkGray,)

                }


            }}

        },

        topBar = {
            if(currentScreen != "Landing Page"){
                Surface( shadowElevation = 10.dp){TopAppBar(title = { Text(textToDisplay, color= Color(0xFF909300),fontWeight = FontWeight.SemiBold,
                    fontFamily = poppinsFamily,
                    fontSize = 25.sp,) },
                    navigationIcon = {
                        if(canNavBack.value){
                            Icon(
                                modifier = Modifier
                                    .clickable { navController.navigateUp() }
                                    .padding(10.dp),
                                imageVector = Icons.Rounded.ArrowBack, contentDescription = "")

                        }
                    },
                    actions = {
                        if(currentScreen == "Home" || currentScreen == "Jobs"){
                                Icon(
                                    Icons.Filled.Search,
                                    contentDescription = "Search",
                                    tint=Color(0xFF909300)
                                )
                            }
                        if(currentScreen!= "Sign Up" && currentScreen!= "Log In"){
                            BadgedBox(modifier = Modifier
                                .clickable { }
                                .padding(18.dp),badge = { Badge { Text("2", color = Color.White, fontSize = 10.sp,) } }) {
                                Icon(
                                    Icons.Filled.Notifications,
                                    contentDescription = "Notification",
                                    tint=Color(0xFF909300)
                                )
                            }}
                    }
                )}
            }
        }
    ) {

        Surface (
            modifier = Modifier
                .fillMaxSize()
                .padding(it),
        )
        {
            NavHost(navController=navController, startDestination = if(isLoggedIn) "Home" else "Landing Page"){
                composable(route = "Landing Page"){
                    canNavBack.value = navController.previousBackStackEntry != null
                    LandingPage {
                        navController.navigate("Sign Up")
                    }
                }
                composable(route = "Sign Up"){
                    canNavBack.value = navController.previousBackStackEntry != null
                    SignUpPage(navToLoginPage = {
                        navController.navigate("Log In")
                    },
                        scope = scope, snackbarHostState = snackbarHostState)
                }

                composable(route = "Log In"){
                    canNavBack.value = navController.previousBackStackEntry != null
                    LoginPage(navToSignUpPage = {
                        navController.navigate("Sign Up")
                    },navToHomePage = {
                        navController.navigate("Home")
                    },scope = scope, snackbarHostState = snackbarHostState)
                }
                composable(route = "Home"){
                    canNavBack.value = navController.previousBackStackEntry != null
                    HomePage(
                        viewDetail = { id ->
                            navController.navigate("Detail/$id")
                        }
                    )

                }
                composable(route = "Jobs") {
                    canNavBack.value = navController.previousBackStackEntry != null
                    JobsPage(
                        viewDetail = { id ->
                            navController.navigate("Answer/$id")
                        },
                        viewApplied = { id ->
                            navController.navigate("Detail/$id")
                        },
                    )
                }
                composable(route = "Profile") {
                    canNavBack.value = navController.previousBackStackEntry != null
                    ProfilePage(navToBack = {
                        navController.navigate(
                            route = "Sign Up",
                            navOptions = NavOptions.Builder()
                                .setPopUpTo("Profile", inclusive = false)
                                .build()
                        )
                    },scope = scope, snackbarHostState = snackbarHostState)
                }

                composable(route = "Detail/{id}",
                    arguments = listOf(
                        navArgument("id"){
                            type = NavType.IntType
                        }
                    ) ) {
                    val id = it.arguments?.getInt("id")
                    canNavBack.value = navController.previousBackStackEntry != null
                    DetailPage(id = id ?: 0,scope = scope,snackbarHostState = snackbarHostState)
                }
                composable(route = "Answer/{id}",
                    arguments = listOf(
                        navArgument("id"){
                            type = NavType.IntType
                        }
                    ) ) {
                    val id = it.arguments?.getInt("id")
                    canNavBack.value = navController.previousBackStackEntry != null
                    AnswerPage(id = id ?: 0,scope = scope,snackbarHostState = snackbarHostState)
                }

            }

        }

    }
}




    // Replace with your logic to check if the user is logged in
    // For simplicity, returning true for demonstration purposes



