package com.budgetapp.budgetapp.data

import arrow.core.Either
import com.budgetapp.budgetapp.data.mapper.toNetworkError
import com.budgetapp.budgetapp.data.model.LinkTokenRequest
import com.budgetapp.budgetapp.data.model.User
import com.budgetapp.budgetapp.data.remote.LinkTokenApi
import com.budgetapp.budgetapp.domain.model.LinkTokenResponse
import com.budgetapp.budgetapp.domain.model.NetworkError
import com.budgetapp.budgetapp.domain.respository.LinkTokenRepository
import javax.inject.Inject

class LinkTokenRepositoryImpl @Inject constructor(
    private val linkTokenApi: LinkTokenApi
): LinkTokenRepository {

    override suspend fun getLinkToken(): Either<NetworkError, LinkTokenResponse> {


        return Either.catch {
            linkTokenApi.getLinkToken()
        }.mapLeft { it.toNetworkError()}
    }
}