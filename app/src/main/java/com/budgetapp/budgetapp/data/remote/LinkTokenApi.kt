package com.budgetapp.budgetapp.data.remote

import com.budgetapp.budgetapp.data.model.LinkTokenRequest
import com.budgetapp.budgetapp.domain.model.LinkTokenResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface LinkTokenApi {

//    @POST("/link/token/create")
//    suspend fun getLinkToken(@Body requestBody: LinkTokenRequest): LinkTokenResponse

    @POST("/link/token/create")
    suspend fun getLinkToken(): LinkTokenResponse
}