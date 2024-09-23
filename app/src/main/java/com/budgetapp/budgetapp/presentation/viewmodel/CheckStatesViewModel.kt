package com.budgetapp.budgetapp.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.budgetapp.budgetapp.domain.model.transaction.Transaction
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import javax.inject.Inject

class CheckStatesViewModel: ViewModel() {

    // MutableStateFlow to track checked states
    private val _checkedStates = MutableStateFlow<Map<Transaction, Boolean>>(emptyMap())
    val checkedStates: StateFlow<Map<Transaction, Boolean>> = _checkedStates.asStateFlow() //type is explicitlly provided since it is managing a complex data structure (map)



    // Derived state to calculate the total sum of checked transactions
    val totalSum: StateFlow<Double> = _checkedStates
        .map { checkedStates ->
            checkedStates.filter { it.value }.keys.sumOf { it.amount }
        }
        //converts the resulting flow into a 'StateFlow' that updates whenever state changes
        .stateIn(viewModelScope, SharingStarted.Lazily, 0.0)

    // Function to initialize or update the checked states with transactions
    fun initializeCheckedStates(transactions: List<Transaction>) {
        _checkedStates.update {
            transactions.associateWith { false }  // Initialize all as unchecked
        }
    }


    // Function to update the checked state of a transaction
    fun updateCheckedState(transaction: Transaction, isChecked: Boolean) {
        _checkedStates.update { currentStates ->
            //allow mods since original 'currentStates' is immutable
            currentStates.toMutableMap().apply {
                this[transaction] = isChecked
            }
        }
    }

    fun uncheckAllTransactions() {
        _checkedStates.value = _checkedStates.value.mapValues { false }
    }
}