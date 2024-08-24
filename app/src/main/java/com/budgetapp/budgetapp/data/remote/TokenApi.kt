package com.budgetapp.budgetapp.data.remote



import com.budgetapp.budgetapp.domain.model.LinkTokenResponse
import com.budgetapp.budgetapp.domain.model.PublicTokenResponse
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Query

interface TokenApi {

    //suspend used to perform async functions
    @POST("/link/token/create")
    suspend fun getLinkToken(): LinkTokenResponse

    @POST("/item/public_token/exchange")
    suspend fun exchangePublicToken(@Query("public_token") publicToken:String): PublicTokenResponse
}

