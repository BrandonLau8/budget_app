package com.budgetapp.budgetapp.presentation.util.components

import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.plaid.link.FastOpenPlaidLink
import com.plaid.link.Plaid
import com.plaid.link.linkTokenConfiguration
import com.plaid.link.result.LinkExit
import com.plaid.link.result.LinkSuccess

@Composable
fun PlaidLinkButton(
    token: String,
    activity: ComponentActivity,
    navController: NavController
) {

    val linkTokenConfiguration = linkTokenConfiguration {
        this.token = token
    }

    // Initialize PlaidHandler with the link token
    val plaidHandler = remember(token) {
        Plaid.create(activity.application, linkTokenConfiguration)
    }


    // Ensure ActivityResultLauncher is registered before the Activity is in RESUMED state
    val launcher = rememberLauncherForActivityResult(contract = FastOpenPlaidLink()) { result ->
        when (result) {
            is LinkSuccess -> handleLinkSuccess(result, navController)
            is LinkExit -> handleLinkExit(result)
        }
    }


    // UI with button to trigger Plaid Link flow
    Button(
        onClick = {
            launcher.launch(plaidHandler)
        },
        modifier = Modifier
            .width(200.dp)  // Set a specific width
            .height(50.dp)  // Set a specific height
            .padding(8.dp)  // Adjust padding as needed
    ) {
        Text("Open Plaid Link")
    }
}


private fun handleLinkSuccess(
    success: LinkSuccess,
    navController: NavController
) {

    Log.d("test", "${success.publicToken}")

    navController.navigate("accessScreen")

}

private fun handleLinkExit(exit: LinkExit) {
    Log.d("test", "error")
}