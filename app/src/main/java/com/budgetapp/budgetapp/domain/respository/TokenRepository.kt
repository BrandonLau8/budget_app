package com.budgetapp.budgetapp.domain.respository

import android.credentials.GetCredentialResponse
import android.net.Network
import androidx.credentials.CustomCredential
import arrow.core.Either
import com.budgetapp.budgetapp.domain.model.CreatePublicTokenResponse

import com.budgetapp.budgetapp.domain.model.LinkTokenResponse
import com.budgetapp.budgetapp.domain.model.NetworkError
import com.budgetapp.budgetapp.domain.model.PayloadDto
import com.budgetapp.budgetapp.domain.model.PublicTokenResponse
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import retrofit2.Response

interface TokenRepository {
    suspend fun getLinkToken(): Response<LinkTokenResponse>

    suspend fun createPublicToken(): Response<CreatePublicTokenResponse>

    suspend fun exchangePublicToken(publicToken:String): Response<PublicTokenResponse>

    suspend fun validateIdToken(idToken: String): Response<PayloadDto>
}