//package com.budgetapp.budgetapp.oauth
//
//import net.openid.appauth.AuthorizationService
//import net.openid.appauth.TokenRequest
//import javax.inject.Inject
//
//class OAuthRepository @Inject constructor(
//    private val authService: AuthorizationService
//) {
//    fun exchangeAuthorizationCode(authCode: String, callback:(String?, Exception?) -> Unit): String {
//        //use authcode to request access token from the OAuth provider
//        authService.performTokenRequest(
//            TokenRequest.Builder(serviceConfig, "your-client-id")
//                .setAuthorizationCode(authResponse.authorizationCode)
//                .setRedirectUri(Uri.parse("your.package:/oauth2redirect"))
//                .build()
//        ) { tokenResponse, exception ->
//            // Handle token response or error
//        }
//    }
//}