package com.gebeya.sebsab_mobile.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.gebeya.sebsab_mobile.ui.theme.poppinsFamily

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SebsabiApp(

){

    val navController = rememberNavController()
    val canNavBack = remember{ mutableStateOf(false)}
    val currentScreen =  navController.currentBackStackEntryAsState().value?.destination?.route
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    Scaffold(
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState){ data-> Snackbar(snackbarData = data, containerColor = Color(0xFF909300), contentColor = Color.White ) }
        },

        topBar = {
            if(currentScreen != "Landing Page"){
                TopAppBar(title = { Text(currentScreen ?: "ሰብሳቢ", color= Color(0xFF909300),fontWeight = FontWeight.SemiBold,
                    fontFamily = poppinsFamily,
                    fontSize = 20.sp,) },
                    navigationIcon = {
                        if(canNavBack.value){
                            Icon(
                                modifier = Modifier
                                    .clickable { navController.navigateUp() }
                                    .padding(10.dp),
                                imageVector = Icons.Rounded.ArrowBack, contentDescription = "")

                        }
                    },
                )
            }
        }
    ) {

        Surface (
            modifier = Modifier
                .fillMaxSize()
                .padding(it),
        )
        {
            NavHost(navController=navController, startDestination = "Landing Page"){
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
                    },scope = scope, snackbarHostState = snackbarHostState)
                }

            }

        }

    }
}


