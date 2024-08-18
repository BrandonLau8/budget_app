package com.budgetapp.budgetapp.presentation.launchwallet_screen

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Application
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.result.ActivityResultLauncher
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.semantics.Role.Companion.Button
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.budgetapp.budgetapp.presentation.plaidlink_screen.PlaidLinkScreen
import com.budgetapp.budgetapp.presentation.util.components.MyTopAppBar
import com.plaid.link.Plaid
import com.plaid.link.PlaidHandler
import com.plaid.link.linkTokenConfiguration

@Composable
//internal encapsulates within module
internal fun LaunchWalletScreen(
    viewModel: LaunchWalletViewModel = hiltViewModel(),
    activity: ComponentActivity

) {

    //Collects stream of data ('StateFlow') into a state only when Composable is active(on-screen)
    val viewState by viewModel.state.collectAsStateWithLifecycle()


    LaunchWalletContent(
        viewState = viewState,
        onButtonClick = {
            viewModel.getLinkToken() })

    if(viewState.linkToken != null) {

        PlaidLinkScreen(
            token = viewState.linkToken!!,
            activity = activity
            )
    }



//    LaunchWalletContent(
//        viewState = viewState,
//        onButtonClick = {
//            viewModel.getLinkToken()
//            // Launch PlaidLinkActivity
//            val token = viewState.linkToken // Assume viewState contains the token
//            val intent = Intent(context, PlaidLinkActivity::class.java).apply {
//                putExtra("LINK_TOKEN", token)
//            }
//            context.startActivity(intent)
//        }
//    )
}

@Composable
fun LaunchWalletContent(
    viewState: LaunchWalletViewState,
    onButtonClick: () -> Unit,
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = { MyTopAppBar(title = "Budgeting App") }
    ) {

        Column(
            modifier = Modifier.padding(it.calculateTopPadding()),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (viewState.isLoading) {
                CircularProgressIndicator()
            } else {
                Button(
                    onClick = onButtonClick,
                    enabled = viewState.isButtonEnabled
                ) {
                    Text(viewState.buttonText)
                }

                viewState.error?.let {
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(text = it, color = Color.Red)

                }
            }
        }
    }
}