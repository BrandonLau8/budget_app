package com.budgetapp.budgetapp.ui.navigation

import android.app.Activity
import android.content.Context
import androidx.activity.ComponentActivity
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.budgetapp.budgetapp.presentation.access_screen.AccessScreen
import com.budgetapp.budgetapp.presentation.budget_screen.BudgetScreen
import com.budgetapp.budgetapp.presentation.launchwallet_screen.LaunchWalletScreen
import dagger.hilt.android.qualifiers.ActivityContext

@Composable
fun AppNavigation(navController: NavHostController, activity: ComponentActivity) {
    NavHost(navController = navController, startDestination = "launchWallet" ) {
        composable("launchWallet") {
            LaunchWalletScreen(
                activity = activity,
                navController = navController
            )
        }

        composable("accessScreen/{linkToken}") { backStackEntry ->
            val linkToken = backStackEntry.arguments?.getString("linkToken")
            AccessScreen(navController = navController, activity = activity, linkToken=linkToken)
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