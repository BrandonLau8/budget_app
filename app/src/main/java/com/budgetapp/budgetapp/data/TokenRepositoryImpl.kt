package com.budgetapp.budgetapp.data

import arrow.core.Either
import com.budgetapp.budgetapp.data.mapper.toNetworkError
import com.budgetapp.budgetapp.data.remote.TokenApi
import com.budgetapp.budgetapp.domain.model.CreatePublicTokenResponse
import com.budgetapp.budgetapp.domain.model.LinkTokenResponse
import com.budgetapp.budgetapp.domain.model.NetworkError
import com.budgetapp.budgetapp.domain.model.PublicTokenResponse
import com.budgetapp.budgetapp.domain.respository.TokenRepository
import javax.inject.Inject

class TokenRepositoryImpl @Inject constructor(
    private val tokenApi: TokenApi
): TokenRepository {

    override suspend fun getLinkToken(): Either<NetworkError, LinkTokenResponse> {
        //if an exception occurs while calling tokenApi.getLinkToken(), it will be transformed into a NetworkError using the toNetworkError() extension function.
        return Either.catch {
            tokenApi.getLinkToken()
        }.mapLeft { it.toNetworkError()}
    }


    override suspend fun createPublicToken(): Either<NetworkError, CreatePublicTokenResponse> {
        return Either.catch {
            tokenApi.createPublicToken()
        }.mapLeft { it.toNetworkError() }
    }

    override suspend fun exchangePublicToken(publicToken:String): Either<NetworkError, PublicTokenResponse> {
        return Either.catch {
            tokenApi.exchangePublicToken(publicToken)
        }.mapLeft { it.toNetworkError() }
    }
}