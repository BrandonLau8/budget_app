package com.budgetapp.budgetapp.domain.model.transaction

data class TransactionResponse(
    val accounts: List<AccountBase> = emptyList(),
    val added: List<Transaction> = emptyList()
)
