package com.budgetapp.budgetapp.domain.respository

import arrow.core.Either
import com.budgetapp.budgetapp.domain.model.LinkTokenResponse
import com.budgetapp.budgetapp.domain.model.NetworkError

interface LinkTokenRepository {
    suspend fun getLinkToken(): Either<NetworkError, LinkTokenResponse>
}