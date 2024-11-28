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
) {

//    Collects stream of data ('StateFlow') into a state only when Composable is active(on-screen)
    val viewState by viewModel.linkTokenState.collectAsState()

    // Once the link token is available, navigate to AccessScreen
    LaunchedEffect(viewState.linkToken) {
        viewState.linkToken?.let { linkToken ->
            // Passing the linkToken as an argument to AccessScreen
            navController.navigate("accessScreen/$linkToken")
        }
    }

    LaunchWalletContent(
        viewState = viewState,
        toBudgetScreen = { navController.navigate("budgetScreen") },
        toAccessScreen = { navController.navigate("accessScreen/${viewState.linkToken}") },
        toGoogleSignIn = { viewModel.getCredential(activity) },
        modifier = Modifier.fillMaxSize()
    )
}

@Composable
fun LaunchWalletContent(
    viewState: LinkTokenState,
    toBudgetScreen: () -> Unit,
    toAccessScreen: () -> Unit,
    toGoogleSignIn: () -> Unit,
    modifier: Modifier
) {

    Scaffold(
        topBar = {
            MyTopAppBar(
                title = "Budgeting App",
                toBudgetScreen = toBudgetScreen,
                toAccessScreen = toAccessScreen,
                showNavigationIcon = false,
                showBudgetScreen = false,
            )
        },
        content = { innerPadding ->

            Column(
                modifier = modifier
                    .fillMaxSize()
                    .padding(innerPadding),
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
                }
            }
        }
                    )



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