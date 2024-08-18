//package com.budgetapp.budgetapp
//
//import android.os.Bundle
//import androidx.activity.result.ActivityResultLauncher
//import androidx.appcompat.app.AppCompatActivity
//import com.plaid.link.FastOpenPlaidLink
//import com.plaid.link.Plaid
//import com.plaid.link.PlaidHandler
//import com.plaid.link.linkTokenConfiguration
//import com.plaid.link.result.LinkExit
//import com.plaid.link.result.LinkSuccess
//
//class PlaidLinkActivity : AppCompatActivity() {
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//
//        // Retrieve the link token from the Intent
//        val token = intent.getStringExtra("LINK_TOKEN")
//        token?.let {
//            // Launch the Plaid Link with the token
//            launchPlaidLink(it)
//        }
//    }
//
//    //register a callback that will be triggered when the activity or component completes its task.
//    private val linkAccountToPlaid =
//        //FastOpenPlaidLink() is an ActivityResultContract
//        registerForActivityResult(FastOpenPlaidLink()) { result ->
//            //e.g. result = LinkSuccess OR result = LinkExit
//            when (result) {
//                is LinkSuccess -> handleLinkSuccess(result)
//                is LinkExit -> handleLinkExit(result)
//            }
//        }
//
//    private fun launchPlaidLink(token: String) {
//        val linkTokenConfiguration = linkTokenConfiguration {
//            this.token = token
//        }
//        val plaidHandler: PlaidHandler = Plaid.create(application, linkTokenConfiguration)
//        linkAccountToPlaid.launch(plaidHandler)
//    }
//
//    private fun handleLinkSuccess(success: LinkSuccess) {
//        val details = success.publicToken
//    }
//
//    private fun handleLinkExit(exit: LinkExit) {
//        val error = exit.error
//    }
//}
