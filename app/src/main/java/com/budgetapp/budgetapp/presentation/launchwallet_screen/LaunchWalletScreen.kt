package com.budgetapp.budgetapp.presentation.launchwallet_screen

import androidx.activity.ComponentActivity
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.budgetapp.budgetapp.presentation.access_screen.AccessViewModel
import com.budgetapp.budgetapp.presentation.budget_screen.BudgetViewModel
import com.budgetapp.budgetapp.presentation.util.components.PlaidLinkButton
import com.budgetapp.budgetapp.presentation.util.components.MyTopAppBar

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


    // Function to create PlaidLinkButton
    val plaidLinkButton: @Composable () -> Unit = {
        viewState.linkToken?.let { token ->
            PlaidLinkButton(
                token = token,
                activity = activity,
                navController = navController,
                viewModel = accessViewModel

            )
        }
    }

    LaunchWalletContent(
        viewState = viewState,
        onButtonClick = {
            viewModel.getLinkToken()
        },
        plaidLinkButton = plaidLinkButton,
        toBudgetScreen = {navController.navigate("budgetScreen")},
        toAccessScreen = {navController.navigate("accessScreen")},
    )
}

@Composable
fun LaunchWalletContent(
    viewState: LinkTokenState,
    onButtonClick: () -> Unit,
    toBudgetScreen: () -> Unit,
    toAccessScreen: () -> Unit,
    plaidLinkButton: (@Composable () -> Unit)? = null
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = { MyTopAppBar(
            title = "Budgeting App",
            toBudgetScreen = toBudgetScreen,
            toAccessScreen = toAccessScreen,
            showNavigationIcon = false,
            showBudgetScreen = false,
        ) }
    ) {paddingValues ->

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
                        onClick = onButtonClick,
                        enabled = viewState.isButtonEnabled,
                        modifier = Modifier
                            .padding(bottom = 8.dp)
                    ) {
                        Text(viewState.buttonText)
                    }

               if(viewState.linkToken != null) {
                   // Display the PlaidLinkButton if provided
                   plaidLinkButton?.invoke()
               }

                viewState.error?.let {
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(text = it)

                }
            }
//            Button(onClick = { insertBudget() }) {
//                Icon(imageVector = Icons.Filled.Add, contentDescription = "add")
//            }
        }
    }
}