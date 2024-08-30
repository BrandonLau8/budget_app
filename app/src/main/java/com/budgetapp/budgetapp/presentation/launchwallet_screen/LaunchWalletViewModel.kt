package com.budgetapp.budgetapp.presentation.launchwallet_screen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.budgetapp.budgetapp.domain.respository.TokenRepository
import com.budgetapp.budgetapp.presentation.util.sendEvent
import com.budgetapp.budgetapp.util.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LaunchWalletViewModel @Inject constructor(
    private val tokenRepository: TokenRepository
) : ViewModel() {

    private val _linkTokenState = MutableStateFlow(LinkTokenState())
    val linkTokenState = _linkTokenState.asStateFlow()

    fun getLinkToken() {
        viewModelScope.launch {
            _linkTokenState.update { it.copy(isLoading = true, isButtonEnabled = false) }

            val result = tokenRepository.getLinkToken()

            Log.d("test", "getLinkToken result: $result")

            result.fold(
                { error ->
                    _linkTokenState.update {
                        it.copy(
                            isLoading = false,
                            error = error.error.message,
                            isButtonEnabled = true
                        )
                    }

                    sendEvent(Event.Toast(error.error.message))
                    Log.d("test", "getLinkToken: ${error.error.message}")
                },
                { response ->
                    _linkTokenState.update {
                        it.copy(
                            isLoading = false,
                            buttonText = "Token: ${response.link_token}",
                            isButtonEnabled = true,
                            linkToken = response.link_token
                        )
                    }
                    Log.d("test", "getLinkToken: ${response.link_token}")
                }
            )
            _linkTokenState.update {
                it.copy(isLoading = false)
            }
        }
    }


}