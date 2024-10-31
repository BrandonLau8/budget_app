package com.budgetapp.budgetapp.data.remote



import android.credentials.GetCredentialResponse
import com.budgetapp.budgetapp.domain.model.CreatePublicTokenResponse
import com.budgetapp.budgetapp.domain.model.LinkTokenResponse
import com.budgetapp.budgetapp.domain.model.PublicTokenResponse
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Query

interface TokenApi {

    //suspend used to perform async functions
    @POST("/link/token/create")
    suspend fun getLinkToken(): LinkTokenResponse

    @POST("/sandbox/public_token/create")
    suspend fun createPublicToken(): CreatePublicTokenResponse

    @POST("/item/public_token/exchange")
    suspend fun exchangePublicToken(@Query("public_token") publicToken:String): PublicTokenResponse

    @POST("/validate_id_token")
    suspend fun validateIdToken(@Body credential: GoogleIdTokenCredential): Response<GetCredentialResponse>
}

