package com.budgetapp.budgetapp.presentation.access_screen

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.budgetapp.budgetapp.presentation.util.components.MyTopAppBar

@Composable
internal fun AccessScreen(
    navController: NavController,
    viewModel: AccessViewModel = hiltViewModel(navController.getBackStackEntry("launchWallet"))
) {

    val viewState by viewModel.accessViewState.collectAsStateWithLifecycle()

    // Debug: Log the access token to verify its value
    LaunchedEffect(viewState.accessToken) {
        Log.d("test","Access Token: ${viewState.accessToken}")
    }

    // Debug: Log the state when it changes
    LaunchedEffect(viewState) {
        Log.d("test","Access ViewState Changed: ${viewState.accessToken}")
    }





    AccessContent(viewState = viewState)
}

@Composable
fun AccessContent(
    viewState: AccessViewState
) {

    Column(
        modifier = Modifier
            .padding(10.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        // Ensure to show some text even if accessToken is null, for debugging
        val displayText = viewState.accessToken ?: "Access token is not available"
        Text(displayText)
    }
}

