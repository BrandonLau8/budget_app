package com.budgetapp.budgetapp.presentation.access_screen

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ListItemColors
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import arrow.core.left
import com.budgetapp.budgetapp.domain.model.transaction.Transaction
import com.budgetapp.budgetapp.domain.model.transaction.TransactionsSyncResponse
import com.budgetapp.budgetapp.presentation.util.components.MyTopAppBar
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Composable
internal fun AccessScreen(
    navController: NavController,
    viewModel: AccessViewModel = hiltViewModel(navController.getBackStackEntry("launchWallet")),

) {

    // used to observe and collect state from the viewmodel. convert into format ('State
    val viewState by viewModel.accessViewState.collectAsStateWithLifecycle()
    val checkedStates by viewModel.checkedStates.collectAsStateWithLifecycle()
    val totalSum by viewModel.totalSum.collectAsStateWithLifecycle()

//    // Debug: Log the access token to verify its value
//    LaunchedEffect(viewState.accessToken) {
//        Log.d("test","Access Token: ${viewState.accessToken}")
//    }
//


    when (viewState) {
        is AccessViewState.TransactionViewState -> {
            AccessContent(
                transactions = (viewState as AccessViewState.TransactionViewState).transactions,
                checkedStates = checkedStates,
                onCheckedChange = { transaction, isChecked ->
                    viewModel.updateCheckedState(transaction, isChecked)
                },
                totalSum = totalSum,
                modifier = Modifier.fillMaxSize(),

                onUncheckAllClick = {
                    viewModel.uncheckAllTransactions() // Call the function to uncheck all
                },
                toBudgetScreen = {navController.navigate("budgetScreen")}
            )
        }

        is AccessViewState.Loading -> {
            CircularProgressIndicator()
        }

        is AccessViewState.Error -> {

        }
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
            Transaction( 50.0, "USD", localDate, "Sample Transaction 2")
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
        toBudgetScreen = {}
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
    toBudgetScreen: () -> Unit
) {


    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = { MyTopAppBar(
            title = "Budgeting App",
            toBudgetScreen = toBudgetScreen

        ) }
    ) { paddingValues ->

        val topPadding = paddingValues.calculateTopPadding()

        Column(modifier = Modifier
            .padding(paddingValues)) {
            Row(
                modifier = Modifier.padding(paddingValues)
            ) {
                NumberContainer(number = totalSum, modifier = Modifier.padding(5.dp), onUncheckAllClick = onUncheckAllClick)
            }


            Text(
                text = "May 2024",
                modifier = Modifier
                    .padding(bottom = 10.dp)
                    .align(Alignment.Start)
            )




            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.Start
            ) {
                items(transactions.added) { transaction ->
                    val isChecked by rememberUpdatedState(checkedStates[transaction] ?: false)



                    ListItem(
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

@Composable
fun NumberContainer(
    number: Double,
    modifier: Modifier = Modifier,
    onUncheckAllClick: () -> Unit, // Add this parameter
) {
    Surface(
        modifier = modifier
            .size(200.dp) // Set the size of the container
            .padding(16.dp), // Padding inside the container
        shape = RoundedCornerShape(8.dp), // Rounded corners
        color = Color(0xFFBBDEFB), // Background color of the container


    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center, // Center items vertically
            horizontalAlignment = Alignment.CenterHorizontally // Center items horizontally
        ) {
            Text(
                text = "Amount Spent",
                style = MaterialTheme.typography.titleMedium,
                color = Color.Black,
                modifier = Modifier.padding(bottom = 8.dp) // Space between texts
            )
            Text(
                text = "%.2f".format(number), // Format number to 2 decimal places
                style = MaterialTheme.typography.titleMedium,
                color = Color.Black
            )
        }
    }

    Surface(
        modifier = modifier
            .size(200.dp) // Set the size of the container
            .padding(16.dp) // Padding inside the container
        .clickable { onUncheckAllClick() }, // Make Surface clickable
        shape = RoundedCornerShape(8.dp), // Rounded corners
        color = Color(0xFFFFCDD2), // Background color of the container
    ) {
        // Add the button to uncheck all checkboxes
        Box(
            contentAlignment = Alignment.Center // Center the content inside the container

        ) {

                Text(
                    text = "Uncheck All", // Text for the clickable surface
                    style = MaterialTheme.typography.titleMedium,
                    color = Color.Black
                )


        }
    }

}


@Composable
fun ListItem(
    headlineContent: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    overlineContent: (@Composable () -> Unit)? = null,
    supportingContent: (@Composable () -> Unit)? = null,
    leadingContent: (@Composable () -> Unit)? = null,
    trailingContent: (@Composable () -> Unit)? = null,
    colors: ListItemColors = ListItemDefaults.colors(),
    tonalElevation: Dp = ListItemDefaults.Elevation,
    shadowElevation: Dp = ListItemDefaults.Elevation,
    checkboxState: Boolean = false, // Add checkboxState parameter
    onCheckboxCheckedChange: (Boolean) -> Unit = {}, // Callback for checkbox state change
) {
    Row(
        modifier = modifier
            .padding(8.dp)
            .clip(RoundedCornerShape(4.dp))
            .background(color = colors.containerColor.copy(alpha = 0.1f))
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .shadow(tonalElevation, RoundedCornerShape(4.dp))
    ) {
        leadingContent?.invoke()
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(start = if (leadingContent != null) 16.dp else 0.dp)
        ) {
            overlineContent?.invoke()
            headlineContent()
            supportingContent?.invoke()
        }
        trailingContent?.invoke()
        Checkbox(
            checked = checkboxState,
            onCheckedChange = onCheckboxCheckedChange
        )
    }
}


