package com.budgetapp.budgetapp.presentation.launchwallet_screen

import android.content.Context
import android.credentials.GetCredentialException
import androidx.credentials.GetCredentialRequest
import android.util.Log
import androidx.credentials.CredentialManager
import androidx.credentials.GetPasswordOption
import androidx.credentials.GetPublicKeyCredentialOption
import androidx.credentials.PasswordCredential
import androidx.credentials.PublicKeyCredential
import androidx.credentials.exceptions.NoCredentialException
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.budgetapp.budgetapp.domain.respository.TokenRepository
import com.budgetapp.budgetapp.presentation.util.sendEvent
import com.budgetapp.budgetapp.util.Constant.clientId
import com.budgetapp.budgetapp.util.Event
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LaunchWalletViewModel @Inject constructor(
    private val tokenRepository: TokenRepository,
    private val credentialManager: CredentialManager
) : ViewModel() {

    private val _linkTokenState = MutableStateFlow(LinkTokenState())
    val linkTokenState = _linkTokenState.asStateFlow()

    val googleIdOption: GetGoogleIdOption = GetGoogleIdOption.Builder()
        .setFilterByAuthorizedAccounts(false)
        .setServerClientId(clientId)
        .setAutoSelectEnabled(true)
    .build()

    // Retrieves the user's saved password for your app from their
    // password provider.
    val getPasswordOption = GetPasswordOption()

    // Get passkey from the user's public key credential provider.
//    val getPublicKeyCredentialOption = GetPublicKeyCredentialOption(
//        requestJson = requestJson
//    )

//    val getCredRequest = androidx.credentials.GetCredentialRequest(
//        listOf(getPasswordOption, getPublicKeyCredentialOption)
//    )

    val request: GetCredentialRequest = GetCredentialRequest.Builder()
        .addCredentialOption(googleIdOption)
        .build()

    fun getCredential(activityContext: Context) {
        viewModelScope.launch {
            try {
                val result = credentialManager.getCredential(
                    // Use an activity-based context to avoid undefined system UI launching behavior
                    context = activityContext,
                    request = request

                )
                handleSignIn(result)
            } catch (e: NoCredentialException) {
                Log.e("CredentialManager", "No credential available", e)
            }
        }
    }

    private fun handleSignIn(result: androidx.credentials.GetCredentialResponse) {
        // Handle the successfully returned credential.
        val credential = result.credential

        when (credential) {
            is PublicKeyCredential -> {
                val responseJson = credential.authenticationResponseJson
                // Share responseJson i.e. a GetCredentialResponse on your server to
                // validate and  authenticate
            }
            is PasswordCredential -> {
                val username = credential.id
                val password = credential.password
                // Use id and password to send to your server to validate
                // and authenticate
            }
//            is CustomCredential -> {
//                // If you are also using any external sign-in libraries, parse them
//                // here with the utility functions provided.
//                if (credential.type == ExampleCustomCredential.TYPE)  {
//                    try {
//                        val ExampleCustomCredential = ExampleCustomCredential.createFrom(credential.data)
//                        // Extract the required credentials and complete the authentication as per
//                        // the federated sign in or any external sign in library flow
//                    } catch (e: ExampleCustomCredential.ExampleCustomCredentialParsingException) {
//                        // Unlikely to happen. If it does, you likely need to update the dependency
//                        // version of your external sign-in library.
//                        Log.e(TAG, "Failed to parse an ExampleCustomCredential", e)
//                    }
//                } else {
//                    // Catch any unrecognized custom credential type here.
//                    Log.e(TAG, "Unexpected type of credential")
//                }
//            } else -> {
//            // Catch any unrecognized credential type here.
//            Log.e(TAG, "Unexpected type of credential")
//        }
        }
    }

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