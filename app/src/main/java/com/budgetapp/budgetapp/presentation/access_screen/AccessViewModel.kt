package com.budgetapp.budgetapp.presentation.access_screen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.budgetapp.budgetapp.domain.respository.TokenRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AccessViewModel @Inject constructor(
    private val tokenRepository: TokenRepository
) : ViewModel() {


    private val _accessViewState = MutableStateFlow(AccessViewState())
    val accessViewState = _accessViewState.asStateFlow()

    fun exchangePublicToken(publicToken:String) {
        viewModelScope.launch {

            val result = tokenRepository.exchangePublicToken(publicToken)

            result.fold(
                {error ->
                    Log.e("AccessViewModel", "Token exchange failed: $error")
                },
                {response ->
                    Log.d("AccessViewModel", "Token exchange successful: ${response.access_token}")
                    _accessViewState.update {
                        val newState = it.copy(
                            accessToken = response.access_token
                        )
                        Log.d("AccessViewModel", "Updated accessViewState: $newState")
                        newState
                    }
                }
            )



            Log.d("test", "AccessToken: $result")
        }
    }
}