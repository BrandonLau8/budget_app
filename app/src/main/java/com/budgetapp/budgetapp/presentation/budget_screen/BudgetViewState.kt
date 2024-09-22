package com.budgetapp.budgetapp.presentation.budget_screen

import androidx.compose.runtime.Stable
import com.budgetapp.budgetapp.domain.model.savedbudget.BudgetItem


@Stable
sealed interface BudgetViewState {
    data class SavedBudgetViewState(val budgetItems: List<BudgetItem> = emptyList()): BudgetViewState

    //object creates singleton class
    object Error: BudgetViewState
    object Loading: BudgetViewState
}