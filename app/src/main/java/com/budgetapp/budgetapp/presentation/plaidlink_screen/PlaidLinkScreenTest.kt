//package com.budgetapp.budgetapp.presentation.plaidlink_screen
//
//import android.util.Log
//import androidx.activity.ComponentActivity
//import androidx.activity.result.ActivityResultLauncher
//import androidx.compose.material3.Button
//import androidx.compose.material3.Text
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.remember
//import com.plaid.link.FastOpenPlaidLink
//import com.plaid.link.Plaid
//import com.plaid.link.PlaidHandler
//import com.plaid.link.linkTokenConfiguration
//import com.plaid.link.result.LinkExit
//import com.plaid.link.result.LinkSuccess
//
//@Composable
//fun PlaidLinkScreenTest(
//    activity: ComponentActivity,
//    linkToken: String) {
//
////    // Ensure the token is not null before proceeding
////    if (linkToken == null) {
////        Log.e("PlaidLinkScreenTest", "Link token is null")
////        return
////    }
////
////    //Get the application context from the activity
////    val application = activity.application
////
////    val linkTokenConfiguration = linkTokenConfiguration {
////        this.token = linkToken
////    }
////    val plaidHandler: PlaidHandler = Plaid.create(application, linkTokenConfiguration)
////
////    val launcher = remember {
////        activity.registerForActivityResult(FastOpenPlaidLink()) { result ->
////            when (result) {
////                is LinkSuccess -> handleLinkSuccess(result)
////                is LinkExit -> handleLinkExit(result)
////            }
////        }
////    }
//
//    // UI code here, e.g., a button to trigger the Plaid Link flow
//    Button(onClick = {
//        // Trigger the launcher
//        launcher.launch(PlaidHandler)
//    }) {
//        Text("Open Plaid Link")
//    }
//}
//
//private fun handleLinkSuccess(success: LinkSuccess) {
//    val publicToken = success.publicToken
//    Log.d("test", "$publicToken")
//}
//
//private fun handleLinkExit(exit: LinkExit) {
//    Log.d("test", "error")
//}