package com.budgetapp.budgetapp.presentation.launchwallet_screen

import android.annotation.SuppressLint
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
import androidx.compose.ui.semantics.Role.Companion.Button
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.budgetapp.budgetapp.presentation.util.components.MyTopAppBar

@Composable
//internal encapsulates within module
internal fun LaunchWalletScreen(
    viewModel: LaunchWalletViewModel = hiltViewModel()
) {
    val viewState by viewModel.state.collectAsStateWithLifecycle()

    LaunchWalletContent(viewState = viewState, onButtonClick = {viewModel.getLinkToken()})
}

@Composable
fun LaunchWalletContent(
    viewState: LaunchWalletViewState,
    onButtonClick: () -> Unit
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
        if(viewState.isLoading) {
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