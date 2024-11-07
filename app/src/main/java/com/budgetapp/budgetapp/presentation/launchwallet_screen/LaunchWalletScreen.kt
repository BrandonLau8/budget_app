package com.budgetapp.budgetapp.presentation.launchwallet_screen

import android.app.Activity
import android.content.Context
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.budgetapp.budgetapp.presentation.access_screen.AccessViewModel
import com.budgetapp.budgetapp.presentation.access_screen.AccessViewState
import com.budgetapp.budgetapp.presentation.budget_screen.BudgetViewModel
import com.budgetapp.budgetapp.presentation.util.components.CredentialSignInScreen
import com.budgetapp.budgetapp.presentation.util.components.PlaidLinkButton
import com.budgetapp.budgetapp.presentation.util.components.MyTopAppBar

import com.plaid.link.FastOpenPlaidLink
import com.plaid.link.Plaid
import com.plaid.link.linkTokenConfiguration
import com.plaid.link.result.LinkExit
import com.plaid.link.result.LinkSuccess

@Composable
//internal encapsulates within module
internal fun LaunchWalletScreen(
    viewModel: LaunchWalletViewModel = hiltViewModel(),
    activity: ComponentActivity,
    navController: NavHostController,
    accessViewModel: AccessViewModel = hiltViewModel(),
    budgetViewModel: BudgetViewModel = hiltViewModel(),
) {

//    Collects stream of data ('StateFlow') into a state only when Composable is active(on-screen)
    val viewState by viewModel.linkTokenState.collectAsStateWithLifecycle()


//    // Function to create PlaidLinkButton
//    val plaidLinkButton: @Composable () -> Unit = {
//        viewState.linkToken?.let { token ->
//            PlaidLinkButton(
//                token = token,
//                activity = activity,
//                navController = navController,
//                viewModel = accessViewModel
//
//            )
//        }
//    }

//    CredentialSignInScreen(activity = activity, viewModel = viewModel)

    LaunchWalletContent(
        viewState = viewState,
        toBudgetScreen = {navController.navigate("budgetScreen")},
        toAccessScreen = {navController.navigate("accessScreen")},
        toGoogleSignIn = {viewModel.getCredential(activity)},
    )
}

@Composable
fun LaunchWalletContent(
    viewState: LinkTokenState,
    toBudgetScreen: () -> Unit,
    toAccessScreen: () -> Unit,
    toGoogleSignIn: ()-> Unit,
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            MyTopAppBar(
                title = "Budgeting App",
                toBudgetScreen = toBudgetScreen,
                toAccessScreen = toAccessScreen,
                showNavigationIcon = false,
                showBudgetScreen = false,
            )
        }
    ) { paddingValues ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            if (viewState.isLoading) {
                CircularProgressIndicator()
            } else {
                Button(
                    onClick = toGoogleSignIn,
                    enabled = viewState.isButtonEnabled,
                    modifier = Modifier
                        .padding(bottom = 8.dp)
                ) {
                    Text(viewState.buttonText)
                }

                LaunchedEffect(viewState.linkToken) {
                    viewState.linkToken?.let {
                        toAccessScreen()
                    }
                }
//                if (viewState.linkToken != null) {
//                    toAccessScreen.invoke()
////                   // Display the PlaidLinkButton if provided
////                   plaidLinkButton?.invoke()
//                }

//                viewState.error?.let {
//                    Spacer(modifier = Modifier.height(16.dp))
//                    Text(text = it)
//                }
//            }

            }
        }
    }
}