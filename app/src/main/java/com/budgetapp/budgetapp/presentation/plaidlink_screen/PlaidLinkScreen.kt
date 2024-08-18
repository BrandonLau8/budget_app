package com.budgetapp.budgetapp.presentation.plaidlink_screen

import android.content.Context
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import com.plaid.link.FastOpenPlaidLink
import com.plaid.link.Plaid
import com.plaid.link.PlaidHandler
import com.plaid.link.linkTokenConfiguration
import com.plaid.link.result.LinkExit
import com.plaid.link.result.LinkSuccess

@Composable
fun PlaidLinkScreen(
    token: String,
    activity: ComponentActivity,
) {

            val linkTokenConfiguration = linkTokenConfiguration {
                this.token = token
            }
//            val plaidHandler: PlaidHandler = remember {Plaid.create(application, linkTokenConfiguration)}

//            // Set up the ActivityResultLauncher
//            val launcher = remember {
//                activity.registerForActivityResult(FastOpenPlaidLink()) { result ->
//                    when (result) {
//                        is LinkSuccess -> handleLinkSuccess(result)
//                        is LinkExit -> handleLinkExit(result)
//                    }
//                }
//            }

    // Initialize PlaidHandler with the link token
    val plaidHandler = remember(token) {
        Plaid.create(activity.application, linkTokenConfiguration)}


    // Ensure ActivityResultLauncher is registered before the Activity is in RESUMED state
    val launcher = rememberLauncherForActivityResult(contract = FastOpenPlaidLink()) { result ->
        when (result) {
            is LinkSuccess -> handleLinkSuccess(result)
            is LinkExit -> handleLinkExit(result)
        }
    }



    // UI with button to trigger Plaid Link flow
    Button(onClick = {
        plaidHandler.open(activity)
    }) {
        Text("Open Plaid Link")
    }
}


private fun handleLinkSuccess(success: LinkSuccess) {
    val publicToken = success.publicToken
    Log.d("test", "$publicToken")
}

private fun handleLinkExit(exit: LinkExit) {
    Log.d("test", "error")
}