package com.budgetapp.budgetapp.presentation.budget_screen

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ListItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.budgetapp.budgetapp.domain.model.savedbudget.BudgetItem

import com.budgetapp.budgetapp.presentation.util.components.MyTopAppBar
import com.budgetapp.budgetapp.presentation.viewmodel.CheckStatesViewModel

@Composable
internal fun BudgetScreen(
    navController: NavController,
    viewModel: BudgetViewModel = hiltViewModel(navController.getBackStackEntry("budgetScreen")),
    checkedStatesViewModel: CheckStatesViewModel = hiltViewModel(),
) {
    val viewState by viewModel.budgetState.collectAsStateWithLifecycle()
    val totalSum by checkedStatesViewModel.totalSum.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.getAllBudgetItems()
    }

    when (val currentState = viewState) {
        is BudgetViewState.SavedBudgetViewState -> {
            val budgetItems = currentState.budgetItems
            BudgetContent(
                modifier = Modifier,
                toBudgetScreen = {},
                toAccessScreen = { navController.navigate("accessScreen") },
                budgetItems = budgetItems,
                deleteBudget = { budgetItem -> viewModel.deleteBudgetItem(budgetItem) }
            )
        }

        BudgetViewState.Error -> TODO()
        BudgetViewState.Loading -> {
            CircularProgressIndicator()
        }
    }

}

@Composable
fun BudgetContent(
    modifier: Modifier,
    toBudgetScreen: () -> Unit,
    toAccessScreen: () -> Unit,
    budgetItems: List<BudgetItem>,
    deleteBudget: (BudgetItem) -> Unit,
) {

    MyTopAppBar(
        title = "Budgetting App",
        toBudgetScreen = toBudgetScreen,
        toAccessScreen = toAccessScreen,
        showNavigationIcon = true,
        showBudgetScreen = true,
        content = { modifier ->

            Column(
                modifier = modifier.fillMaxSize()
            ) {
                budgetItems.forEach { budgetItem ->
                    ListItem(
                        headlineContent = {
                            Text(text = "${budgetItem.amount}")
                        },
                        supportingContent = { Text(text = "${budgetItem.date.toString()}") },
                        trailingContent = {
                            Button(onClick = {
                                deleteBudget(budgetItem)
                                Log.d("db", "deleted")
                            }) {
                                Text(text = "delete")
                            }
                        }

                    )
                }
            }

        }
    )
}



