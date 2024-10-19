package com.budgetapp.budgetapp.data

import androidx.lifecycle.LiveData
import com.budgetapp.budgetapp.data.dao.BudgetDao
import com.budgetapp.budgetapp.domain.model.savedbudget.BudgetItem

interface BudgetRepository {

    suspend fun addBudgetItem(budgetItem: BudgetItem)

    suspend fun getAllBudgetItems(): List<BudgetItem>

    suspend fun deleteBudgetItem(budgetItem: BudgetItem)
}
