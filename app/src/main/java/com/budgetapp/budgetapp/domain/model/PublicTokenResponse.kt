package com.budgetapp.budgetapp.domain.model

data class PublicTokenResponse(
    val access_token: String,
    val item_id: String,
    val request_id: String
)
