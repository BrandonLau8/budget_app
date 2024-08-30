package com.budgetapp.budgetapp.domain.respository

import android.net.Network
import arrow.core.Either
import com.budgetapp.budgetapp.domain.model.CreatePublicTokenResponse

import com.budgetapp.budgetapp.domain.model.LinkTokenResponse
import com.budgetapp.budgetapp.domain.model.NetworkError
import com.budgetapp.budgetapp.domain.model.PublicTokenResponse

interface TokenRepository {
    suspend fun getLinkToken(): Either<NetworkError, LinkTokenResponse>

    suspend fun createPublicToken(): Either<NetworkError, CreatePublicTokenResponse>

    suspend fun exchangePublicToken(publicToken:String): Either<NetworkError, PublicTokenResponse>
}