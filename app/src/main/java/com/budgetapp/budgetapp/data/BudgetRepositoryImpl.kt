package com.budgetapp.budgetapp.data

import androidx.lifecycle.LiveData
import com.budgetapp.budgetapp.data.dao.BudgetDao
import com.budgetapp.budgetapp.domain.model.savedbudget.BudgetItem
import javax.inject.Inject

class BudgetRepositoryImpl @Inject constructor(
    private val budgetDao: BudgetDao
): BudgetRepository{
    override suspend fun addBudgetItem(budgetItem: BudgetItem) {
        budgetDao.addBudgetItem(budgetItem)
    }

    override suspend fun getAllBudgetItems(): List<BudgetItem> {
        return budgetDao.getAllBudgetItems()
    }

    override suspend fun deleteBudgetItem(budgetItem: BudgetItem) {
        return budgetDao.deleteBudgetItem(budgetItem)
    }
}