package com.budgetapp.budgetapp.presentation.access_screen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope

import com.budgetapp.budgetapp.domain.model.transaction.TransactionsSyncResponse
import com.budgetapp.budgetapp.domain.respository.TokenRepository
import com.budgetapp.budgetapp.domain.respository.TransactionRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


//A ViewModel is a class in Android's Architecture Components that is designed to store and manage UI-related data in a lifecycle-conscious way.
// The primary goal of a ViewModel is to separate the UI data from the UI controllers (like Activities and Fragments),
// making it easier to handle configuration changes (e.g., screen rotations) and maintain a clean architecture.
@HiltViewModel
class AccessViewModel @Inject constructor(
    private val tokenRepository: TokenRepository,
    private val transactionRepository: TransactionRepository,
) : ViewModel() {

    //A 'StateFlow' is a special kind of 'Flow' designed to hold and emit a current state and is often used for managing state in reactive manner.
    private val _accessViewState =
        MutableStateFlow<AccessViewState>(AccessViewState.Empty) //holds current state of type AccessViewState
    val accessViewState = _accessViewState.asStateFlow() //changes state to read  only


    fun exchangePublicToken(publicToken: String) {

        // Set the state to Loading when syncing transactions
        _accessViewState.update { AccessViewState.Loading }

        viewModelScope.launch {
            try {
                val response = tokenRepository.exchangePublicToken(publicToken)
                Log.d(
                    "AccessViewModel",
                    "Token exchange successful: ${response.body()?.access_token}"
                )
                response.body()?.let { syncTransactions(it.access_token) }

            } catch (e: Exception) {
                Log.e("AccessViewModel", "Token exchange failed: $e")
            }
        }
    }

    fun syncTransactions(accessToken: String) {

        _accessViewState.update { AccessViewState.Loading }

        viewModelScope.launch {

            try {
                val response = transactionRepository.syncTransaction(accessToken)
                if (response.isSuccessful && response.body() != null) {
                    val transactionResponse = response.body()!!.added
                    Log.d("AccessViewModel", transactionResponse.toString())

//                        val sampleTransactions = listOf(
//                            Transaction(
//                                amount = 51.76,
//                                isoCurrencyCode = "USD",
//                                date = LocalDate.now(), // Use a string or parse it to LocalDate as needed
//                                name = "H Mart",
//                            ),
//                            Transaction(
//                                amount = 15.43,
//                                isoCurrencyCode = "USD",
//                                date = LocalDate.now(), // Use a string or parse it to LocalDate as needed
//                                name = "Popeyes",
//                            ),
//                            Transaction(
//                                amount = 32.89,
//                                isoCurrencyCode = "USD",
//                                date = LocalDate.now(), // Use a string or parse it to LocalDate as needed
//                                name = "Macy's",
//                            ),
//                            Transaction(
//                                amount = 54.36,
//                                isoCurrencyCode = "USD",
//                                date = LocalDate.now(), // Use a string or parse it to LocalDate as needed
//                                name = "Geico Car Insurance",
//                            ),
//                            Transaction(
//                                amount = 106.32,
//                                isoCurrencyCode = "USD",
//                                date = LocalDate.now(), // Use a string or parse it to LocalDate as needed
//                                name = "Uniqlo",
//                            )
//
//
//                        )
//
//                        val sampleResponse = TransactionsSyncResponse(
//                            added = sampleTransactions
//                        )

                    val trueResponse = TransactionsSyncResponse(added = transactionResponse)

                    _accessViewState.update {
                        AccessViewState.TransactionViewState(transactions = trueResponse)
                    }
                }
            } catch (e: Exception) {

                Log.d("AccessViewModel", "$e")
            }
        }
    }

}