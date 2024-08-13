package com.budgetapp.budgetapp.presentation.launchwallet_screen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.budgetapp.budgetapp.domain.respository.LinkTokenRepository
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
    private val linkTokenRepository: LinkTokenRepository
) : ViewModel() {

    private val _state = MutableStateFlow(LaunchWalletViewState())
    val state = _state.asStateFlow()

    fun getLinkToken() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, isButtonEnabled = false) }

            val result = linkTokenRepository.getLinkToken()

            Log.d("test", "getLinkToken result: $result")

            result.fold(
                { error ->
                    _state.update {
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
                    _state.update {
                        it.copy(
                            isLoading = false,
                            buttonText = "Token: ${response.link_token}",
                            isButtonEnabled = true
                        )
                    }
                    Log.d("test", "getLinkToken: ${response.link_token}")
                }
            )
            _state.update {
                it.copy(isLoading = false)
            }
//           try {
//               val token = linkTokenRepository.getLinkToken()
//               _state.update { it.copy(isLoading = false, buttonText = "Token: $token", isButtonEnabled = true) }
//           } catch (e:Exception) {
//               _state.update { it.copy(isLoading = false, error = "Failed", isButtonEnabled = true) }
//           }
        }
    }
}