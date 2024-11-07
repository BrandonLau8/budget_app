package com.budgetapp.budgetapp.data

import android.credentials.GetCredentialResponse
import androidx.credentials.CustomCredential
import arrow.core.Either
import com.budgetapp.budgetapp.data.mapper.toNetworkError
import com.budgetapp.budgetapp.data.remote.TokenApi
import com.budgetapp.budgetapp.domain.model.CreatePublicTokenResponse
import com.budgetapp.budgetapp.domain.model.LinkTokenResponse
import com.budgetapp.budgetapp.domain.model.NetworkError
import com.budgetapp.budgetapp.domain.model.PayloadDto
import com.budgetapp.budgetapp.domain.model.PublicTokenResponse
import com.budgetapp.budgetapp.domain.respository.TokenRepository
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import retrofit2.Response
import javax.inject.Inject

class TokenRepositoryImpl @Inject constructor(
    private val tokenApi: TokenApi,
): TokenRepository {

    override suspend fun getLinkToken(): Response<LinkTokenResponse> {
        //if an exception occurs while calling tokenApi.getLinkToken(), it will be transformed into a NetworkError using the toNetworkError() extension function.
        return tokenApi.getLinkToken()
    }


    override suspend fun createPublicToken(): Response<CreatePublicTokenResponse> {
        return tokenApi.createPublicToken()
    }

    override suspend fun exchangePublicToken(publicToken:String): Response<PublicTokenResponse> {
        return tokenApi.exchangePublicToken(publicToken)
    }

    override suspend fun validateIdToken(idToken: String): Response<PayloadDto> {
        return tokenApi.validateIdToken(idToken)
    }
}