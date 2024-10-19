package com.budgetapp.budgetapp.presentation.budget_screen

import android.app.Application
import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.budgetapp.budgetapp.data.BudgetRepository
import com.budgetapp.budgetapp.data.dao.BudgetDao
import com.budgetapp.budgetapp.data.database.BudgetDatabase
import com.budgetapp.budgetapp.domain.model.savedbudget.BudgetItem
import com.budgetapp.budgetapp.presentation.viewmodel.CheckStatesViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject



data class TestBudgetViewState (
    val budgetItem: List<BudgetItem> = emptyList()
)

@HiltViewModel
class BudgetViewModel @Inject constructor(
    private val budgetRepository: BudgetRepository,
): ViewModel() {

    private val _budgetState = MutableStateFlow<BudgetViewState>(BudgetViewState.Loading)
    val budgetState: StateFlow<BudgetViewState> = _budgetState.asStateFlow()

    fun getAllBudgetItems() {
//        _budgetState.update { currentState ->
//            currentState.copy(
//                budgetItem = listOf(
//                    BudgetItem(
//                        amount = 23.31
//                    ),
//                    BudgetItem(
//                        amount = 546.32
//                    ),
//                    BudgetItem(
//                        amount = 63.21
//                    )
//                ))
//        }
        // Set the state to loading when the data fetch starts
        _budgetState.value = BudgetViewState.Loading
        viewModelScope.launch {
            try{
                val budgetItems = budgetRepository.getAllBudgetItems()
                _budgetState.value = BudgetViewState.SavedBudgetViewState(budgetItems = budgetItems)
            } catch (e: Exception) {

            }
        }
    }

    fun insertBudgetItem(budgetItem: BudgetItem){
        // Set the state to loading when the data fetch starts
        _budgetState.value = BudgetViewState.Loading
        viewModelScope.launch() {

            try {
                budgetRepository.addBudgetItem(budgetItem = budgetItem)
            } catch (e: Exception) {
                Log.d("db", e.message.toString())
            }

        }
    }

    fun deleteBudgetItem(budgetItem: BudgetItem){
        viewModelScope.launch {
            try {
                budgetRepository.deleteBudgetItem(budgetItem)

                //Immediately update the list in memory
                val updatedItems = _budgetState.value
                    .let { state ->
                        if(state is BudgetViewState.SavedBudgetViewState) {
                            state.budgetItems.filter{it.id != budgetItem.id} //filter out the deleted item
                        } else emptyList()
                    }

                //emit the updated list to trigger UI recomposition
                _budgetState.value = BudgetViewState.SavedBudgetViewState(updatedItems)

            } catch (e: Exception) {
                Log.d("db", e.message.toString())
            }
        }
    }
}