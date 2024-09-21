package com.budgetapp.budgetapp.presentation.budget_screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.budgetapp.budgetapp.presentation.access_screen.NumberContainer
import com.budgetapp.budgetapp.presentation.util.components.MyTopAppBar

@Composable
internal fun BudgetScreen(
    navController: NavController,
//    viewModel: BudgetViewModel = hiltViewModel(navController.getBackStackEntry("budgetScreen")),
) {
//    val viewState by viewModel.budgetState.collectAsStateWithLifecycle()
//
//    when (viewState) {
//        is BudgetViewState.SavedBudgetViewState -> {
//            Text(text = "hello")
//        }
//
//        BudgetViewState.Error -> TODO()
//        BudgetViewState.Loading -> TODO()
//    }
    BudgetContent(modifier = Modifier) {
        
    }
}

@Composable
fun BudgetContent(
    modifier: Modifier,
    toBudgetScreen: () -> Unit,
) {
    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            MyTopAppBar(
                title = "Budgetting App",
                toBudgetScreen = toBudgetScreen
            )
        }) { paddingValues ->
        val topPadding = paddingValues.calculateTopPadding()
        Column(
            modifier = Modifier
                .padding(paddingValues)
        ) {
            Row(
                modifier = Modifier.padding(paddingValues)
            ) {
                Text(text = "hello")
            }
        }

    }

}

