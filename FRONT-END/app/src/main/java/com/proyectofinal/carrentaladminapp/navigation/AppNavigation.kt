package com.proyectofinal.carrentaladminapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.proyectofinal.carrentaladminapp.login.LoginUserNameScreen
import com.proyectofinal.carrentaladminapp.ui.cars.NewCarScreen
import com.proyectofinal.carrentaladminapp.ui.login.LoginPasswordScreen
import com.proyectofinal.carrentaladminapp.ui.components.WelcomeScreen
import com.proyectofinal.carrentaladminapp.ui.home.HomeAdminScreen
import com.proyectofinal.carrentaladminapp.ui.register.RegisterScreen

@Composable
fun AppNavigation(navController: NavHostController) {

    NavHost( navController, startDestination = "home") {
        composable("welcome") { WelcomeScreen(navController) }
        composable("login_username") { LoginUserNameScreen(navController) }
        composable("login_password/{username}", arguments = listOf(navArgument("username"){
            defaultValue = "" })
        ) { backStackEntry ->
            val username = backStackEntry.arguments?.getString("username") ?: ""
            LoginPasswordScreen(navController, username)}
        composable("register") { RegisterScreen(navController) }
        composable("home") { HomeAdminScreen(navController)}
        composable("newcar") { NewCarScreen(navController) }
    }
}



