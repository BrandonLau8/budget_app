package com.budgetapp.budgetapp.presentation.budget_screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
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

import com.budgetapp.budgetapp.presentation.access_screen.ListItem
import com.budgetapp.budgetapp.presentation.access_screen.NumberContainer
import com.budgetapp.budgetapp.presentation.util.components.MyTopAppBar

@Composable
internal fun BudgetScreen(
    navController: NavController,
    viewModel: BudgetViewModel = hiltViewModel(navController.getBackStackEntry("budgetScreen")),
) {
    val viewState by viewModel.budgetState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.getAllBudgetItems()
    }

    when (val currentState = viewState) {
        is BudgetViewState.SavedBudgetViewState -> {
            val budgetItems = currentState.budgetItems
            BudgetContent(
                modifier = Modifier,
                toBudgetScreen = {},
                toLaunchScreen = {navController.navigate("launchWallet")},
                budgetItems = budgetItems
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
    toLaunchScreen:() -> Unit,
    budgetItems: List<BudgetItem>
) {
    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            MyTopAppBar(
                title = "Budgetting App",
                toBudgetScreen = toBudgetScreen,
                toLaunchScreen = toLaunchScreen
            )
        }) { paddingValues ->
        val topPadding = paddingValues.calculateTopPadding()
        Column(
            modifier = Modifier
                .padding(paddingValues)
        ) {budgetItems.forEach{budgetItem ->
            Text(text = "${budgetItem.amount}")
                }
            }
        }
    }



