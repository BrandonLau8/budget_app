package com.budgetapp.budgetapp.presentation.access_screen


import com.budgetapp.budgetapp.domain.model.transaction.TransactionsSyncResponse
import retrofit2.Response

sealed interface AccessViewState {
    data class TransactionViewState(val transactions: TransactionsSyncResponse): AccessViewState

    //object creates singleton class
    object Error: AccessViewState
    object Loading: AccessViewState
}

//// Function to map API response to view state
//fun mapResponseToViewState(response: Response<TransactionResponse>): AccessViewState {
//    return if (response.isSuccessful && response.body() != null) {
//        val transactionResponse = response.body()!!
//        AccessViewState.TransactionViewState(transactions = transactionResponse)
//    } else {
//        AccessViewState.Error
//    }
//}