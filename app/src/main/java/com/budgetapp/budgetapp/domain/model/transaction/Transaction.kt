package com.budgetapp.budgetapp.domain.model.transaction

import com.google.gson.annotations.SerializedName
import java.time.LocalDate

data class Transaction(
//    @SerializedName("accountId") val accountId: String,
    @SerializedName("amount") val amount: Double,
    @SerializedName("isoCurrencyCode") val isoCurrencyCode: String,
//    @SerializedName("unofficialCurrencyCode") val unofficialCurrencyCode: String?,
//    @SerializedName("category") val category: List<String>,
//    @SerializedName("categoryId") val categoryId: Int?,
//    @SerializedName("checkNumber") val checkNumber: String?,
    @SerializedName("date") val date: LocalDate, // Requires conversion from String
//    @SerializedName("location") val location: Location?,
    @SerializedName("name") val name: String,
//    @SerializedName("merchantName") val merchantName: String?,
//    @SerializedName("originalDescription") val originalDescription: String?,
//    @SerializedName("paymentMeta") val paymentMeta: PaymentMeta?,
//    @SerializedName("pending") val pending: Boolean,
//    @SerializedName("pendingTransactionId") val pendingTransactionId: String?,
//    @SerializedName("accountOwner") val accountOwner: String?,
//    @SerializedName("transactionId") val transactionId: String,
//    @SerializedName("transactionType") val transactionType: String,
//    @SerializedName("logoUrl") val logoUrl: String?,
//    @SerializedName("website") val website: String?,
//    @SerializedName("authorizedDate") val authorizedDate: String?,
//    @SerializedName("authorizedDatetime") val authorizedDatetime: String?,
//    @SerializedName("datetime") val datetime: String?,
//    @SerializedName("paymentChannel") val paymentChannel: String,
//    @SerializedName("personalFinanceCategory") val personalFinanceCategory: PersonalFinanceCategory?,
//    @SerializedName("transactionCode") val transactionCode: String?,
//    @SerializedName("personalFinanceCategoryIconUrl") val personalFinanceCategoryIconUrl: String,
//    @SerializedName("counterparties") val counterparties: List<Counterparty>,
//    @SerializedName("merchantEntityId") val merchantEntityId: String?
)

//data class Location(
//    @SerializedName("address") val address: String?,
//    @SerializedName("city") val city: String?,
//    @SerializedName("region") val region: String?,
//    @SerializedName("postalCode") val postalCode: String?,
//    @SerializedName("country") val country: String?,
//    @SerializedName("lat") val lat: Double?,
//    @SerializedName("lon") val lon: Double?,
//    @SerializedName("storeNumber") val storeNumber: String?
//)
//
//data class PaymentMeta(
//    @SerializedName("referenceNumber") val referenceNumber: String?,
//    @SerializedName("ppdId") val ppdId: String?,
//    @SerializedName("payee") val payee: String?,
//    @SerializedName("byOrderOf") val byOrderOf: String?,
//    @SerializedName("payer") val payer: String?,
//    @SerializedName("paymentMethod") val paymentMethod: String?,
//    @SerializedName("paymentProcessor") val paymentProcessor: String?,
//    @SerializedName("reason") val reason: String?
//)
//
//data class PersonalFinanceCategory(
//    @SerializedName("primary") val primary: String,
//    @SerializedName("detailed") val detailed: String,
//    @SerializedName("confidenceLevel") val confidenceLevel: String
//)
//
//data class Counterparty(
//    val data: Map<String, Any>? = null
//)

