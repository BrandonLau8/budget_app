package com.budgetapp.budgetapp.presentation.util.components

import android.content.Context
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.hilt.navigation.compose.hiltViewModel
import com.budgetapp.budgetapp.presentation.launchwallet_screen.LaunchWalletViewModel
import com.budgetapp.budgetapp.presentation.launchwallet_screen.LinkTokenState

@Composable
fun CredentialSignInScreen(
    viewModel: LaunchWalletViewModel = hiltViewModel(),
    activityContext: Context // Passed from the Activity
) {
    val linkTokenState by viewModel.linkTokenState.collectAsState()

    // Display a sign-in button or other UI elements
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Button(
            onClick = {
                // Trigger the credential retrieval when the button is clicked
                viewModel.getCredential(activityContext)
            }
        ) {
            Text("Sign In")
        }

//        // Optionally, show state such as loading or errors
//        when (linkTokenState) {
//            is LinkTokenState.Loading -> {
//                CircularProgressIndicator()
//            }
//            is LinkTokenState.Error -> {
//                Text("Error: ${(linkTokenState as LinkTokenState.Error).message}")
//            }
//            else -> {
//                // Handle other UI states if needed
//            }
//        }
    }
}
