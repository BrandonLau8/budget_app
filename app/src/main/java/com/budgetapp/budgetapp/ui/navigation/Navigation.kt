package com.budgetapp.budgetapp.ui.navigation

import androidx.activity.ComponentActivity
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.budgetapp.budgetapp.presentation.access_screen.AccessScreen
import com.budgetapp.budgetapp.presentation.budget_screen.BudgetScreen
import com.budgetapp.budgetapp.presentation.launchwallet_screen.LaunchWalletScreen

@Composable
fun AppNavigation(navController: NavHostController, activity: ComponentActivity) {
    NavHost(navController = navController, startDestination = "launchWallet" ) {
        composable("launchWallet") {
            LaunchWalletScreen(
                activity = activity,
                navController = navController)
        }

        composable("accessScreen") {
            AccessScreen(navController = navController)
        }

        composable("budgetScreen") {
            BudgetScreen(navController = navController)
        }
    }
}

@Composable
fun MainActivityContent(activity: ComponentActivity) {
    val navController = rememberNavController()
    AppNavigation(navController = navController, activity = activity)
}