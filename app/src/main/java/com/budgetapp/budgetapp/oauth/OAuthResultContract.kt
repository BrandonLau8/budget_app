//package com.budgetapp.budgetapp.oauth
//
//import android.content.Context
//import android.content.Intent
//import androidx.activity.result.contract.ActivityResultContract
//import net.openid.appauth.AuthorizationException
//import net.openid.appauth.AuthorizationResponse
//
//class OAuthResultContract : ActivityResultContract<Unit, OAuthResult>() {
//    override fun createIntent(context: Context, input: Unit?): Intent {
//        val authRequest = buildAuthRequest()
//        return authService.getAuthorizationRequestIntent(authRequest)
//    }
//
//    override fun parseResult(resultCode: Int, intent: Intent?): OAuthResult {
//        val authResponse = AuthorizationResponse.fromIntent(intent)
//        val authException = AuthorizationException.fromIntent(intent)
//        return OAuthResult(authResponse, authException)
//    }
//}