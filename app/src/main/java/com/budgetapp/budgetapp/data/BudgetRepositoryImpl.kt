package com.budgetapp.budgetapp.data

import androidx.lifecycle.LiveData
import com.budgetapp.budgetapp.data.dao.BudgetDao
import com.budgetapp.budgetapp.domain.model.savedbudget.BudgetItem
import com.budgetapp.budgetapp.domain.model.savedbudget.SavedBudgets
import javax.inject.Inject

class BudgetRepositoryImpl @Inject constructor(
    private val budgetDao: BudgetDao
): BudgetRepository{
    override suspend fun addBudgetItem(budgetItem: BudgetItem) {
        budgetDao.addBudgetItem(budgetItem)
    }

    override fun getAllBudgetItems(): LiveData<SavedBudgets> {
        return budgetDao.getAllBudgetItems()
    }
}