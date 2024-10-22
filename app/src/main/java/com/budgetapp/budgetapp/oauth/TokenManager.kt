//package com.budgetapp.budgetapp.oauth
//
//import android.content.Context
//
//class TokenManager(context: Context) {
//    private val sharedPreferences = context.getSharedPreferences("auth_prefs", Context.MODE_PRIVATE)
//
//    fun saveAccessToken(token: String) {
//        sharedPreferences.edit().putString("access_token", token).apply()
//    }
//
//    fun getAccessToken(): String?{
//        return sharedPreferences.getString("access_token", null)
//    }
//}