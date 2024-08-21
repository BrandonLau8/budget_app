package com.budgetapp.budgetapp.data.remote

import com.budgetapp.budgetapp.data.model.LinkTokenRequest
import com.budgetapp.budgetapp.domain.model.LinkTokenResponse
import com.budgetapp.budgetapp.domain.model.PublicTokenResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface LinkTokenApi {

    //suspend used to perform async functions
    @POST("/link/token/create")
    suspend fun getLinkToken(): LinkTokenResponse

    @POST("/item/public_token/exchange")
    suspend fun exchangePublicToken(): PublicTokenResponse
}

