package com.budgetapp.budgetapp

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResultLauncher
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.repeatOnLifecycle
import com.budgetapp.budgetapp.presentation.launchwallet_screen.LaunchWalletScreen
import com.budgetapp.budgetapp.ui.navigation.MainActivityContent
import com.budgetapp.budgetapp.ui.theme.BudgetAppTheme
import com.budgetapp.budgetapp.util.Event
import com.budgetapp.budgetapp.util.EventBus
import com.plaid.link.FastOpenPlaidLink
import com.plaid.link.Plaid
import com.plaid.link.PlaidHandler
import com.plaid.link.linkTokenConfiguration
import com.plaid.link.result.LinkExit
import com.plaid.link.result.LinkSuccess
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge() //allows app to extend past status and navigation bar on screen
        setContent {
            BudgetAppTheme {

                //Ensuring that coroutines and other asynchronous tasks are appropriately started and stopped based on the lifecycle state.
                val lifecycle = LocalLifecycleOwner.current.lifecycle

                //Used to launch coroutines within the scope of a composable
                LaunchedEffect(key1 = lifecycle) {//runs everytime lifecycle object changes but that is rare for lifecycle

                    //ensure that the collect operation only runs when the composable's lifecycle is in the 'STARTED' state
                    repeatOnLifecycle(state = Lifecycle.State.STARTED) {

                        EventBus.events.collect { event ->
                            if (event is Event.Toast) {
                                Toast.makeText(this@MainActivity, event.message, Toast.LENGTH_SHORT)
                                    .show()
                            }
                        }
                    }
                }

                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainActivityContent(activity = this)
                }
            }
        }
    }


}

