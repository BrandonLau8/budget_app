package com.budgetapp.budgetapp.presentation.saved_screen

import com.budgetapp.budgetapp.domain.model.savedbudget.SavedBudgets
import com.budgetapp.budgetapp.presentation.access_screen.AccessViewState

sealed interface SavedViewState {
    data class SavedBudgetViewState(val savedBudgets: SavedBudgets): SavedViewState

    //object creates singleton class
    object Error: SavedViewState
    object Loading: SavedViewState
}