package com.budgetapp.budgetapp.presentation.launchwallet_screen

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.credentials.GetCredentialException
import androidx.credentials.GetCredentialRequest
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.runtime.remember
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
import com.budgetapp.budgetapp.domain.model.PayloadDto
import com.budgetapp.budgetapp.domain.respository.TokenRepository
import com.budgetapp.budgetapp.presentation.util.sendEvent
import com.budgetapp.budgetapp.util.Constant
import com.budgetapp.budgetapp.util.Constant.CLIENT_ID
import com.budgetapp.budgetapp.util.Event
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GetSignInWithGoogleOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.android.libraries.identity.googleid.GoogleIdTokenParsingException
import com.plaid.link.FastOpenPlaidLink
import com.plaid.link.Plaid
import com.plaid.link.linkTokenConfiguration
import com.plaid.link.result.LinkExit
import com.plaid.link.result.LinkSuccess
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LaunchWalletViewModel @Inject constructor(
    private val tokenRepository: TokenRepository,
    private val credentialManager: CredentialManager,
) : ViewModel() {

    private val _linkTokenState = MutableStateFlow(LinkTokenState())
    val linkTokenState = _linkTokenState.asStateFlow()


    //Google Sign In
    val googleIdOption: GetGoogleIdOption =
        GetGoogleIdOption.Builder().setFilterByAuthorizedAccounts(false)
            .setServerClientId(CLIENT_ID).setAutoSelectEnabled(true).build()

    val request: GetCredentialRequest =
        GetCredentialRequest.Builder().addCredentialOption(googleIdOption).build()

    fun getCredential(googleActivity: ComponentActivity) {
        viewModelScope.launch {
            Log.d("CredentialManager", "getCredential called")
            try {
                val result = credentialManager.getCredential(
                    context = googleActivity, request = request
                )
                Log.d("CredentialManager", "Credential obtained: $result")

                handleSignIn(result)
            } catch (e: NoCredentialException) {
                Log.e("CredentialManager", "No credential available", e)
            } catch (e: Exception) {
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
                        val googleIdTokenCredential =
                            GoogleIdTokenCredential.createFrom(credential.data)
                        Log.d("handleSignIn", googleIdTokenCredential.toString())

                        val response =
                            tokenRepository.validateIdToken(googleIdTokenCredential.idToken)

                        if (response.isSuccessful) {
                            Log.d("handleSignIn", "Response: $response")
                            val sub = response.body()?.sub
                            Log.d("handleSignIn", "User ID (sub): $sub")

                            _linkTokenState.update {
                                it.copy(
                                    userId = sub
                                )
                            }

                            if (sub != null) {
                                getLinkToken(sub)
                            }

                        }
                    } catch (e: GoogleIdTokenParsingException) {
                        Log.e("handleSignIn", "Received an invalid google id token response", e)
                    }
                } else {
                    // Catch any unrecognized custom credential type here.
                    Log.e("handleSignIn", "Unexpected type of credential")
                }
            }

            else -> {
                // Catch any unrecognized credential type here.
                Log.e("handleSignIn", "Unexpected type of credential")
            }
        }
    }


    fun getLinkToken(userId: String) {
        if(userId != null) {
            viewModelScope.launch {
                try {
                    val response = tokenRepository.getLinkToken()
                    Log.d("getLinkToken", "LinkToken: $response")
                    _linkTokenState.update {
                        it.copy(
                            linkToken = response.body()?.link_token
                        )
                    }
                } catch (e: Exception) {
                    Log.e("getLinkToken", "An error occurred", e)
                    sendEvent(Event.Toast("$e"))
                }
            }
        }
    }

//    fun getLinkToken() {
//        viewModelScope.launch {
//            _linkTokenState.update { it.copy(isLoading = true, isButtonEnabled = false) }
//
//            val result = tokenRepository.getLinkToken()
//
//            Log.d("test", "getLinkToken result: $result")
//
//            result.fold({ error ->
//                _linkTokenState.update {
//                    it.copy(
//                        isLoading = false, error = error.error.message, isButtonEnabled = true
//                    )
//                }
//
//                sendEvent(Event.Toast(error.error.message))
//                Log.d("test", "getLinkToken: ${error.error.message}")
//            }, { response ->
//                _linkTokenState.update {
//                    it.copy(
//                        isLoading = false,
//                        isButtonEnabled = true,
//                        linkToken = response.link_token
//                    )
//                }
//                Log.d("test", "getLinkToken: ${response.link_token}")
//            })
//            _linkTokenState.update {
//                it.copy(isLoading = false)
//            }
//        }
//    }


}