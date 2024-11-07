package com.budgetapp.budgetapp.presentation.access_screen


import com.budgetapp.budgetapp.domain.model.transaction.TransactionsSyncResponse

sealed interface AccessViewState {
    data class TransactionViewState(val transactions: TransactionsSyncResponse) : AccessViewState

    //object creates singleton class
    object Error: AccessViewState
    object Loading: AccessViewState
}