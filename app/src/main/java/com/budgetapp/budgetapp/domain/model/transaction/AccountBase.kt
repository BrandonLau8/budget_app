package com.budgetapp.budgetapp.domain.model.transaction

data class AccountBase(
    val accountId: String,
    val balances: AccountBalance,
    val mask: Int,
    val name: String,
    val officialName: String?,
    val type: String,
    val subtype: String?,
    val verificationStatus: String?, // Adjust type if needed
    val verificationInsights: String?, // Adjust type if needed
    val persistentAccountId: String?
)

data class AccountBalance(
    val available: Double?,
    val current: Double?,
    val limit: Double?,
    val isoCurrencyCode: String?,
    val unofficialCurrencyCode: String?,
    val lastUpdatedDatetime: String? // Adjust type based on your needs
)
