package com.budgetapp.budgetapp.presentation.access_screen

import android.annotation.SuppressLint
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
import androidx.compose.runtime.getValue
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
import com.budgetapp.budgetapp.presentation.util.components.CustomListItem
import com.budgetapp.budgetapp.presentation.util.components.MyTopAppBar
import com.budgetapp.budgetapp.presentation.util.components.NumberContainer
import com.budgetapp.budgetapp.presentation.viewmodel.CheckStatesViewModel
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Composable
internal fun AccessScreen(
    navController: NavController,
    viewModel: AccessViewModel = hiltViewModel(navController.getBackStackEntry("launchWallet")),
    budgetViewModel: BudgetViewModel = hiltViewModel(),
    checkedStatesViewModel: CheckStatesViewModel = hiltViewModel(),
) {

    // used to observe and collect state from the viewmodel. convert into format ('State
    val viewState by viewModel.accessViewState.collectAsStateWithLifecycle()
    val checkedStates by checkedStatesViewModel.checkedStates.collectAsStateWithLifecycle()
    val totalSum by checkedStatesViewModel.totalSum.collectAsStateWithLifecycle()

    when (viewState) {
        is AccessViewState.TransactionViewState -> {
            val transactions = (viewState as AccessViewState.TransactionViewState).transactions
            AccessContent(
                transactions = transactions,
                checkedStates = checkedStates,
                onCheckedChange = { transaction, isChecked ->
                    checkedStatesViewModel.updateCheckedState(transaction, isChecked)
                },
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
                }
            )
        }

//        is AccessViewState.Loading -> {
//            CircularProgressIndicator()
//        }
//
//        is AccessViewState.Error -> {
//
//        }
    }
}

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
        transactions = dummyTransactions,
        checkedStates = checkedStates,
        onCheckedChange = { _, _ -> },
        totalSum = totalSum,
        modifier = Modifier.fillMaxSize(),
        onUncheckAllClick = {},
        toBudgetScreen = {},
        toAccessScreen = {},
        insertBudget = {},
    )
}


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun AccessContent(
    transactions: TransactionsSyncResponse,
    checkedStates: Map<Transaction, Boolean>,
    onCheckedChange: (Transaction, Boolean) -> Unit,
    totalSum: Double,
    modifier: Modifier,
    onUncheckAllClick: () -> Unit, // Add this parameter
    toBudgetScreen: () -> Unit,
    toAccessScreen: () -> Unit,
    insertBudget: () -> Unit
) {
    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            MyTopAppBar(
                title = "Budgeting App",
                toBudgetScreen = toBudgetScreen,
                toAccessScreen = toAccessScreen,
                showNavigationIcon = true,
                showBudgetScreen = true,
            )
        }
    ) { paddingValues ->

        val topPadding = paddingValues.calculateTopPadding()

        Column(
            modifier = Modifier
                .padding(paddingValues)
        ) {
            Row {
                NumberContainer(
                    number = totalSum,
                    modifier = Modifier,
                    onUncheckAllClick = { onUncheckAllClick() },
                    insertBudget = { insertBudget ()}
                )
            }

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
    }
}

