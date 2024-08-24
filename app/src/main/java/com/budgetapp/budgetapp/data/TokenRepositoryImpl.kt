package com.budgetapp.budgetapp.data

import arrow.core.Either
import com.budgetapp.budgetapp.data.mapper.toNetworkError
import com.budgetapp.budgetapp.data.remote.TokenApi
import com.budgetapp.budgetapp.domain.model.LinkTokenResponse
import com.budgetapp.budgetapp.domain.model.NetworkError
import com.budgetapp.budgetapp.domain.model.PublicTokenResponse
import com.budgetapp.budgetapp.domain.respository.TokenRepository
import javax.inject.Inject

class TokenRepositoryImpl @Inject constructor(
    private val tokenApi: TokenApi
): TokenRepository {

    override suspend fun getLinkToken(): Either<NetworkError, LinkTokenResponse> {
        return Either.catch {
            tokenApi.getLinkToken()
        }.mapLeft { it.toNetworkError()}
    }

    override suspend fun exchangePublicToken(publicToken:String): Either<NetworkError, PublicTokenResponse> {
        return Either.catch {
            tokenApi.exchangePublicToken(publicToken)
        }.mapLeft { it.toNetworkError() }
    }
}