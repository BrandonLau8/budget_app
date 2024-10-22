//package com.budgetapp.budgetapp.oauth
//
//import androidx.activity.compose.rememberLauncherForActivityResult
//import androidx.compose.foundation.layout.Arrangement
//import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.fillMaxSize
//import androidx.compose.material3.Button
//import androidx.compose.material3.Text
//import androidx.compose.runtime.Composable
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.platform.LocalContext
//import androidx.hilt.navigation.compose.hiltViewModel
//import androidx.navigation.NavController
//
//@Composable
//fun LoginScreen(
//    viewModel: OAuthViewModel = hiltViewModel(),
//    navController: NavController
//) {
//    val context = LocalContext.current
//    val launcher = rememberLauncherForActivityResult(
//        contract = OAuthResultContract()
//    ) { result ->
//        viewModel.handleOAuthResponse(result.response, result.exception!!)
//    }
//
//    Column(
//        modifier = Modifier.fillMaxSize(),
//        horizontalAlignment = Alignment.CenterHorizontally,
//        verticalArrangement = Arrangement.Center
//    ) {
//        Button(onClick = {viewModel.startOAuthFlow(context)}) {
//            Text(text = "Sign in with OAuth")
//        }
//    }
//}
//
