package com.budgetapp.budgetapp.domain.model

data class CreatePublicTokenResponse(
    val public_token: String,
    val request_id: String
)
