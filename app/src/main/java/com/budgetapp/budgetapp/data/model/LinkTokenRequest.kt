package com.budgetapp.budgetapp.data.model


data class LinkTokenRequest(
    val android_package_name: String,
    val client_id: String,
    val country_codes: Array<String>, // Use arrayOf() for arrays
    val language: String,
    val products: Array<String>, // Use arrayOf() for arrays
    val secret: String,
    val user: User, // Properly initialize User
    val client_name: String
)

data class User(
    val client_user_id: String
)