package com.budgetapp.budgetapp.domain.model

data class LinkTokenResponse(
    val expiration: String,
    val link_token: String,
    val request_id: String
)