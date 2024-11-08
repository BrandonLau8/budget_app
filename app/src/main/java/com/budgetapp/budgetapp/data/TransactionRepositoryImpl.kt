package com.budgetapp.budgetapp.data

import android.util.Log
import arrow.core.Either
import com.budgetapp.budgetapp.data.mapper.toNetworkError
import com.budgetapp.budgetapp.data.remote.TransactionApi
import com.budgetapp.budgetapp.domain.model.NetworkError
import com.budgetapp.budgetapp.domain.model.transaction.TransactionsSyncResponse
import com.budgetapp.budgetapp.domain.respository.TransactionRepository
import com.budgetapp.budgetapp.presentation.access_screen.AccessViewState
import retrofit2.Response
import javax.inject.Inject

class TransactionRepositoryImpl @Inject constructor(
    private val transactionApi: TransactionApi
): TransactionRepository {

    override suspend fun syncTransaction(accessToken: String): Response<TransactionsSyncResponse> {
        return transactionApi.syncTransaction(accessToken)
            // Log the raw JSON response
//            response.raw().use { rawResponse ->
//                Log.d("test", "Raw JSON Response: ${rawResponse.body?.string()}")
//            }
//
//            response

    }
}