package com.budgetapp.budgetapp.presentation.launchwallet_screen

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.credentials.GetCredentialException
import androidx.credentials.GetCredentialRequest
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.credentials.CredentialManager
import androidx.credentials.CustomCredential
import androidx.credentials.GetCredentialResponse
import androidx.credentials.GetPasswordOption
import androidx.credentials.GetPublicKeyCredentialOption
import androidx.credentials.PasswordCredential
import androidx.credentials.PublicKeyCredential
import androidx.credentials.exceptions.NoCredentialException
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.budgetapp.budgetapp.BuildConfig
import com.budgetapp.budgetapp.domain.respository.TokenRepository
import com.budgetapp.budgetapp.presentation.util.sendEvent
import com.budgetapp.budgetapp.util.Constant
import com.budgetapp.budgetapp.util.Constant.CLIENT_ID
import com.budgetapp.budgetapp.util.Event
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GetSignInWithGoogleOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.android.libraries.identity.googleid.GoogleIdTokenParsingException
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
        .setServerClientId(CLIENT_ID)
        .setAutoSelectEnabled(true)
    .build()

//    val signInWithGoogleOption: GetSignInWithGoogleOption = GetSignInWithGoogleOption.Builder()
//        .setServerClientId(WEB_CLIENT_ID)
//    .build()

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

    fun getCredential(googleActivity: ComponentActivity) {
        viewModelScope.launch {
            Log.d("CredentialManager", "getCredential called")
            try {
                val result = credentialManager.getCredential(
                    context = googleActivity,
                    request = request
                )
                Log.d("CredentialManager", "Credential obtained: $result")
                handleSignIn(result)
            } catch (e: NoCredentialException) {
                Log.e("CredentialManager", "No credential available", e)
            }  catch (e: Exception) {
                Log.e("CredentialManager", "An error occurred", e)
            }
        }
    }


    private suspend fun handleSignIn(result: GetCredentialResponse) {
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
            // GoogleIdToken credential
            is CustomCredential -> {
                if (credential.type == GoogleIdTokenCredential.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL) {
                    try {
                        // Use googleIdTokenCredential and extract the ID to validate and
                        // authenticate on your server.
                        val googleIdTokenCredential = GoogleIdTokenCredential
                            .createFrom(credential.data)
                        val response = tokenRepository.validateIdToken(googleIdTokenCredential)
                        Log.d("oauth", credential.toString())
                        if(response.isSuccessful){
                            Log.d("oauth", response.body().toString())

                        }


                    } catch (e: GoogleIdTokenParsingException) {
                        Log.e("oauth", "Received an invalid google id token response", e)
                    }
                } else {
                    // Catch any unrecognized custom credential type here.
                    Log.e("oauth", "Unexpected type of credential")
                }
            }

            else -> {
                // Catch any unrecognized credential type here.
                Log.e("oauth", "Unexpected type of credential")
            }
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