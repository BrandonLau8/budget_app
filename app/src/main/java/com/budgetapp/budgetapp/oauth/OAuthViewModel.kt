//package com.budgetapp.budgetapp.oauth
//
//import android.content.Context
//import android.content.Intent
//import android.net.Uri
//import androidx.compose.runtime.getValue
//import androidx.compose.runtime.mutableStateOf
//import androidx.compose.runtime.setValue
//import androidx.lifecycle.ViewModel
//import dagger.hilt.android.lifecycle.HiltViewModel
//import net.openid.appauth.AuthorizationException
//import net.openid.appauth.AuthorizationRequest
//import net.openid.appauth.AuthorizationResponse
//import net.openid.appauth.AuthorizationService
//import net.openid.appauth.AuthorizationServiceConfiguration
//import net.openid.appauth.ResponseTypeValues
//import javax.inject.Inject
//
//
//@HiltViewModel
//class OAuthViewModel @Inject constructor(
//    private val authService: AuthorizationService,
//    private val repository: OAuthRepository,
//): ViewModel() {
//
//    var serviceConfig: AuthorizationServiceConfiguration = AuthorizationServiceConfiguration(
//        Uri.parse("https://idp.example.com/auth"),  // authorization endpoint
//        Uri.parse("https://idp.example.com/token") // token endpoint
//    )
//
//    var accessToken by mutableStateOf<String?>(null)
//        private set
//
//    var authRequestBuilder: AuthorizationRequest.Builder = AuthorizationRequest.Builder(
//        serviceConfig,  // the authorization service configuration
//        "MY_CLIENT_ID",  // the client ID, typically pre-registered and static
//        ResponseTypeValues.CODE,  // the response_type value: we want a code
//        'MY_REDIRECT_URI'
//    ) // the redirect URI to which the auth response is sent
//
//    fun startOAuthFlow(context: Context) {
//        val authRequest = buildAuthRequest() //define your auth request
//        val authIntent = authService.getAuthorizationRequestIntent(authRequest)
//        context.startActivity(authIntent)
//    }
//
//    fun handleOAuthResult(data: Intent) {
//        //Handle the OAuth result, exchange code for access token
//        val response = AuthorizationResponse.fromIntent(data)
//        val exception = AuthorizationException.fromIntent(data)
//        if(response != null){
//            //Exchange auth code for access token
//        } else {
//            //Handle error
//        }
//    }
//
//    private fun buildAuthRequest(): AuthorizationRequest {
//        //construct AuthorizationRequest for OAuth
//    }
//
//    fun handleOAuthResponse(authResponse: AuthorizationResponse?, exception: AuthorizationException) {
//        if(authResponse != null) {
//            //exchange authResponse for access token
//        }
//    }
//}