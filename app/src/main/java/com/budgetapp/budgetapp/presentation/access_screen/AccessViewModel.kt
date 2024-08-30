package com.budgetapp.budgetapp.presentation.access_screen

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.budgetapp.budgetapp.domain.model.transaction.Transaction
import com.budgetapp.budgetapp.domain.model.transaction.TransactionResponse
import com.budgetapp.budgetapp.domain.respository.TokenRepository
import com.budgetapp.budgetapp.domain.respository.TransactionRepository
import com.google.android.gms.common.api.Response
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class AccessViewModel @Inject constructor(
    private val tokenRepository: TokenRepository,
    private val transactionRepository: TransactionRepository,
) : ViewModel() {

    private val _accessViewState = MutableStateFlow<AccessViewState>(AccessViewState.Loading)
    val accessViewState = _accessViewState.asStateFlow()

    // MutableStateFlow to track checked states
    private val _checkedStates = MutableStateFlow<Map<Transaction, Boolean>>(emptyMap())
    val checkedStates: StateFlow<Map<Transaction, Boolean>> = _checkedStates.asStateFlow()

    // Derived state to calculate the total sum of checked transactions
    val totalSum: StateFlow<Double> = _checkedStates
        .map { checkedStates ->
            checkedStates.filter { it.value }.keys.sumOf { it.amount }
        }
        .stateIn(viewModelScope, SharingStarted.Lazily, 0.0)

    // Function to update the checked state of a transaction
    fun updateCheckedState(transaction: Transaction, isChecked: Boolean) {
        _checkedStates.update { currentStates ->
            currentStates.toMutableMap().apply {
                this[transaction] = isChecked
            }
        }
    }

    fun uncheckAllTransactions() {
        _checkedStates.value = _checkedStates.value.mapValues { false }
    }


    fun exchangePublicToken(publicToken: String) {

        // Set the state to Loading when syncing transactions
        _accessViewState.update { AccessViewState.Loading }

        viewModelScope.launch {

            val result = tokenRepository.exchangePublicToken(publicToken)

            result.fold(
                { error ->
                    Log.e("AccessViewModel", "Token exchange failed: $error")

                },
                { response ->
                    Log.d("AccessViewModel", "Token exchange successful: ${response.access_token}")
                    syncTransactions(response.access_token)
                }
            )
        }
    }

    fun syncTransactions(accessToken: String) {

        _accessViewState.update { AccessViewState.Loading }

        viewModelScope.launch {
            val result = transactionRepository.syncTransaction(accessToken)

            result.fold(
                { error ->
                    Log.d("AccessViewModel", error.error.message)
                },
                { response ->
                    // Check if the response is successful and contains a body
                    if (response.isSuccessful && response.body() != null) {
                        val transactionResponse = response.body()!!
                        Log.d("AccessViewModel", response.body().toString())

                        val sampleTransactions = listOf(
                            Transaction(
                                amount = 51.76,
                                isoCurrencyCode = "USD",
                                date = LocalDate.now(), // Use a string or parse it to LocalDate as needed
                                name = "H Mart",
                            ),
                            Transaction(
                                amount = 15.43,
                                isoCurrencyCode = "USD",
                                date = LocalDate.now(), // Use a string or parse it to LocalDate as needed
                                name = "Popeyes",
                            ),
                            Transaction(
                                amount = 32.89,
                                isoCurrencyCode = "USD",
                                date = LocalDate.now(), // Use a string or parse it to LocalDate as needed
                                name = "Macy's",
                            ),
                            Transaction(
                                amount = 54.36,
                                isoCurrencyCode = "USD",
                                date = LocalDate.now(), // Use a string or parse it to LocalDate as needed
                                name = "Geico Car Insurance",
                            ),
                            Transaction(
                                amount = 106.32,
                                isoCurrencyCode = "USD",
                                date = LocalDate.now(), // Use a string or parse it to LocalDate as needed
                                name = "Uniqlo",
                            )


                        )

                        val sampleResponse = TransactionResponse(
                            added = sampleTransactions
                        )

                        _accessViewState.update {

                            AccessViewState.TransactionViewState(transactions = sampleResponse)
                        }

                        // Initialize checkedStates with the current transactions
                        _checkedStates.value = transactionResponse.added.associateWith { false }
                    }
                }
            )
        }
    }

}