package com.budgetapp.budgetapp.presentation.access_screen

import android.annotation.SuppressLint
import android.os.Build
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.budgetapp.budgetapp.domain.model.savedbudget.BudgetItem
import com.budgetapp.budgetapp.domain.model.transaction.Transaction
import com.budgetapp.budgetapp.domain.model.transaction.TransactionsSyncResponse
import com.budgetapp.budgetapp.presentation.budget_screen.BudgetViewModel
import com.budgetapp.budgetapp.presentation.launchwallet_screen.LaunchWalletViewModel
import com.budgetapp.budgetapp.presentation.launchwallet_screen.LinkTokenState
import com.budgetapp.budgetapp.presentation.util.components.CustomListItem
import com.budgetapp.budgetapp.presentation.util.components.MyBottomAppBar
import com.budgetapp.budgetapp.presentation.util.components.MyTopAppBar
import com.budgetapp.budgetapp.presentation.util.components.NumberContainer
import com.budgetapp.budgetapp.presentation.viewmodel.CheckStatesViewModel
import com.plaid.link.FastOpenPlaidLink
import com.plaid.link.Plaid
import com.plaid.link.linkTokenConfiguration
import com.plaid.link.result.LinkExit
import com.plaid.link.result.LinkSuccess
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
@Composable
internal fun AccessScreen(
    linkToken:String?,
    navController: NavController,
    viewModel: AccessViewModel = hiltViewModel(navController.getBackStackEntry("launchWallet")),
    budgetViewModel: BudgetViewModel = hiltViewModel(),
    checkedStatesViewModel: CheckStatesViewModel = hiltViewModel(),
    activity: ComponentActivity
) {



    // used to observe and collect state from the viewmodel. convert into format ('State
    val viewState by viewModel.accessViewState.collectAsStateWithLifecycle()
    val checkedStates by checkedStatesViewModel.checkedStates.collectAsStateWithLifecycle()
    val totalSum by checkedStatesViewModel.totalSum.collectAsStateWithLifecycle()


    val linkTokenConfiguration = linkTokenConfiguration {
        this.token = linkToken
    }

    // Initialize PlaidHandler with the link token
    val plaidHandler = remember(linkToken) {
        Plaid.create(activity.application, linkTokenConfiguration)
    }

    // Ensure ActivityResultLauncher is registered before the Activity is in RESUMED state
    val launcher = rememberLauncherForActivityResult(contract = FastOpenPlaidLink()) { result ->
        when (result) {
            is LinkSuccess -> {
                viewModel.exchangePublicToken(result.publicToken)

                if (viewState is AccessViewState.TransactionViewState) {
                    Log.d("AccessScreen", "Navigating to accessScreen")
                    navController.navigate("accessScreen")
                }
            }
            is LinkExit -> {}
        }
    }

    when (viewState) {
        is AccessViewState.TransactionViewState -> {
            val transactions = (viewState as AccessViewState.TransactionViewState).transactions
            AccessContent(
//                transactions = transactions,
//                checkedStates = checkedStates,
//                onCheckedChange = { transaction, isChecked ->
//                    checkedStatesViewModel.updateCheckedState(transaction, isChecked)
//                },
                totalSum = totalSum,
                modifier = Modifier.fillMaxSize(),

                onUncheckAllClick = {
                    checkedStatesViewModel.uncheckAllTransactions() // Call the function to uncheck all
                },
                toBudgetScreen = { navController.navigate("budgetScreen") },
                toAccessScreen = { navController.navigate("accessScreen") },
                insertBudget = {
//                    budgetViewModel.insertBudgetItem(BudgetItem(amount = totalSum, date = LocalDate.now().toString()))
//                    checkedStatesViewModel.initializeCheckedStates(transactions = transactions.added)
                },
                toPlaidLink = { launcher.launch(plaidHandler)
                Log.d("plaid", "hello")
                }
            )
        }

        is AccessViewState.Loading -> {
            CircularProgressIndicator()
        }

        is AccessViewState.Error -> {

        }

        is AccessViewState.Empty -> {
            AccessContent(
                totalSum = totalSum,
                modifier = Modifier.fillMaxSize(),
                onUncheckAllClick = {
                    checkedStatesViewModel.uncheckAllTransactions() // Call the function to uncheck all
                },
                toBudgetScreen = { navController.navigate("budgetScreen") },
                toAccessScreen = { navController.navigate("accessScreen") },
                insertBudget = {
                    budgetViewModel.insertBudgetItem(
                        BudgetItem(
                            amount = totalSum,
                            date = LocalDate.now().toString()
                        )
                    )
                },
                toPlaidLink = { launcher.launch(plaidHandler)
                    Log.d("plaid", "hello")}
            )

        }

    }


}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun PreviewAccessScreen() {

    // Example date string
    val dateString = "2024-05-10"

    // Specify the format of the date string
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")

    // Parse the string to LocalDate
    val localDate = LocalDate.parse(dateString, formatter)

    // Pass dummy values for previewing
    val dummyTransactions = TransactionsSyncResponse(
        added = listOf(
            Transaction(100.0, "USD", localDate, "Sample Transaction 1"),
            Transaction(50.0, "USD", localDate, "Sample Transaction 2")
        )
    )
    val checkedStates = mapOf(
        dummyTransactions.added[0] to true,
        dummyTransactions.added[1] to false
    )
    val totalSum = 100.0


    // Render AccessContent with dummy data
    AccessContent(
//        transactions = dummyTransactions,
//        checkedStates = checkedStates,
//        onCheckedChange = { _, _ -> },
        totalSum = totalSum,
        modifier = Modifier.fillMaxSize(),
        onUncheckAllClick = {},
        toBudgetScreen = {},
        toAccessScreen = {},
        insertBudget = {},
        toPlaidLink = {}
    )
}


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun AccessContent(
//    transactions: TransactionsSyncResponse,
//    checkedStates: Map<Transaction, Boolean>,
//    onCheckedChange: (Transaction, Boolean) -> Unit,
    toPlaidLink: () -> Unit,
    totalSum: Double,
    modifier: Modifier,
    onUncheckAllClick: () -> Unit, // Add this parameter
    toBudgetScreen: () -> Unit,
    toAccessScreen: () -> Unit,
    insertBudget: () -> Unit,
) {

    MyTopAppBar(
        title = "Budgeting App",
        toBudgetScreen = toBudgetScreen,
        toAccessScreen = toAccessScreen,
        showNavigationIcon = true,
        showBudgetScreen = true,
        content = { modifier ->

            Column(
                modifier = modifier.fillMaxSize()
            ) {
                Row {
                    NumberContainer(
                        number = totalSum,
                        modifier = Modifier,
                        onUncheckAllClick = { onUncheckAllClick() },
                        insertBudget = { insertBudget() }
                    )
                }
                MyBottomAppBar(
                    number = totalSum,
                    toPlaidLink = {toPlaidLink()}

                )

//            LazyColumn(
//                modifier = Modifier.fillMaxSize(),
//                horizontalAlignment = Alignment.Start
//            ) {
//                items(transactions.added) { transaction ->
//                    val isChecked by rememberUpdatedState(checkedStates[transaction] ?: false)
//
//                    CustomListItem(
//                        headlineContent = {
//                            Text(
//                                text = "${transaction.amount} ${transaction.isoCurrencyCode}",
//                                modifier = Modifier.padding(bottom = 4.dp)
//                            )
//                        },
//                        supportingContent = {
//                            Text(
//                                text = transaction.date.toString(),
//                                style = MaterialTheme.typography.bodySmall,
//                                color = MaterialTheme.colorScheme.secondary
//                            )
//                        },
//                        overlineContent = {
//                            Text(
//                                text = transaction.name,
//                                style = MaterialTheme.typography.bodySmall,
//                                color = MaterialTheme.colorScheme.secondary
//                            )
//                        },
//                        checkboxState = isChecked,
//                        onCheckboxCheckedChange = { checked ->
//                            onCheckedChange(transaction, checked)
//                        },
//                        modifier = Modifier.padding(vertical = 4.dp)
//                    )
            }
        }
    )
}

@Composable
fun TransactionContent(
    transactions: TransactionsSyncResponse,
    checkedStates: Map<Transaction, Boolean>,
    onCheckedChange: (Transaction, Boolean) -> Unit,
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.Start
    ) {
        items(transactions.added) { transaction ->
            val isChecked by rememberUpdatedState(checkedStates[transaction] ?: false)

            CustomListItem(
                headlineContent = {
                    Text(
                        text = "${transaction.amount} ${transaction.isoCurrencyCode}",
                        modifier = Modifier.padding(bottom = 4.dp)
                    )
                },
                supportingContent = {
                    Text(
                        text = transaction.date.toString(),
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.secondary
                    )
                },
                overlineContent = {
                    Text(
                        text = transaction.name,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.secondary
                    )
                },
                checkboxState = isChecked,
                onCheckboxCheckedChange = { checked ->
                    onCheckedChange(transaction, checked)
                },
                modifier = Modifier.padding(vertical = 4.dp)
            )
        }
    }
}

