package com.budgetapp.budgetapp.domain.model.transaction

enum class TransactionsUpdateStatus(val value: String) {
    TRANSACTIONS_UPDATE_STATUS_UNKNOWN("TRANSACTIONS_UPDATE_STATUS_UNKNOWN"),
    NOT_READY("NOT_READY"),
    INITIAL_UPDATE_COMPLETE("INITIAL_UPDATE_COMPLETE"),
    HISTORICAL_UPDATE_COMPLETE("HISTORICAL_UPDATE_COMPLETE"),
    ENUM_UNKNOWN("ENUM_UNKNOWN");

    companion object {
        fun fromValue(value: String): TransactionsUpdateStatus {
            return values().find { it.value == value } ?: ENUM_UNKNOWN
        }
    }
}
