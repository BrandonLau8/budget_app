package com.budgetapp.budgetapp.presentation.budget_screen

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.budgetapp.budgetapp.data.dao.BudgetDao
import com.budgetapp.budgetapp.data.database.BudgetDatabase
import com.budgetapp.budgetapp.domain.model.savedbudget.BudgetItem
import com.budgetapp.budgetapp.domain.model.savedbudget.SavedBudgets
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BudgetViewModel @Inject constructor(
    private val repository: BudgetDao
): ViewModel() {

    val readAllData: LiveData<SavedBudgets> = repository.getAllBudgetItems()

    private val _budgetState = MutableStateFlow<BudgetViewState>(BudgetViewState.Loading)
    val budgetState: StateFlow<BudgetViewState> = _budgetState.asStateFlow()

    fun insertBudgetItem(budgetItem: BudgetItem){
        viewModelScope.launch(Dispatchers.IO) {

            try {
                repository.addBudgetItem(budgetItem)
            } catch (e: Exception) {
                _budgetState.value = BudgetViewState.Error
            }

        }
    }
}