package com.budgetapp.budgetapp.data.remote

import com.budgetapp.budgetapp.domain.model.transaction.TransactionResponse
import retrofit2.Response
import retrofit2.http.POST
import retrofit2.http.Query

interface TransactionApi {

    @POST("/transactions/sync")
    suspend fun syncTransaction(@Query("access_token") accessToken: String): Response<TransactionResponse>
}