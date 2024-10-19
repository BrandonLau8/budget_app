package com.budgetapp.budgetapp.domain.model.savedbudget

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate

@Entity(tableName = "budget_item")
data class BudgetItem(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val amount: Double,
//    val date: LocalDate
)
