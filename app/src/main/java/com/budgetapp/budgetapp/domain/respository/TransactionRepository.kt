package com.budgetapp.budgetapp.domain.respository

import arrow.core.Either
import com.budgetapp.budgetapp.domain.model.NetworkError
import com.budgetapp.budgetapp.domain.model.transaction.TransactionsSyncResponse
import retrofit2.Response

interface TransactionRepository {
    suspend fun syncTransaction(accessToken: String): Response<TransactionsSyncResponse>
}