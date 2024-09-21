package com.budgetapp.budgetapp.presentation.budget_screen

import com.budgetapp.budgetapp.domain.model.savedbudget.SavedBudgets

sealed interface BudgetViewState {
    data class SavedBudgetViewState(val savedBudgets: SavedBudgets): BudgetViewState

    //object creates singleton class
    object Error: BudgetViewState
    object Loading: BudgetViewState
}