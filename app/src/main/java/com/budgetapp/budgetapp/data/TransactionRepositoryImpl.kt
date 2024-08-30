package com.budgetapp.budgetapp.data

import arrow.core.Either
import com.budgetapp.budgetapp.data.mapper.toNetworkError
import com.budgetapp.budgetapp.data.remote.TransactionApi
import com.budgetapp.budgetapp.domain.model.NetworkError
import com.budgetapp.budgetapp.domain.model.transaction.TransactionResponse
import com.budgetapp.budgetapp.domain.respository.TransactionRepository
import com.budgetapp.budgetapp.presentation.access_screen.AccessViewState
import retrofit2.Response
import javax.inject.Inject

class TransactionRepositoryImpl @Inject constructor(
    private val transactionApi: TransactionApi
): TransactionRepository {

    override suspend fun syncTransaction(accessToken: String): Either<NetworkError, Response<TransactionResponse>> {
        return Either.catch {
       transactionApi.syncTransaction(accessToken)

        }.mapLeft { it.toNetworkError() }
    }




}