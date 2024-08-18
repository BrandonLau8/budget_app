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
//        val requestBody = LinkTokenRequest(
//            android_package_name = "com.budgetapp.budgetapp",
//            client_id = "665e8bb2ac817b001bc809eb",
//            country_codes = arrayOf("US"),
//            language = "en",
//            products = arrayOf("auth"),
//            secret = "18b0093cfaeac720de940cbd7c0e28",
//            user = User(client_user_id = "user-id"),
//            client_name = "BudgetApp")

        return Either.catch {
            linkTokenApi.getLinkToken()
        }.mapLeft { it.toNetworkError()}
    }
}