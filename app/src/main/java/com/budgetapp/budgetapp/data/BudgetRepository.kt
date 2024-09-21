package com.budgetapp.budgetapp.data

import androidx.lifecycle.LiveData
import com.budgetapp.budgetapp.data.dao.BudgetDao
import com.budgetapp.budgetapp.domain.model.savedbudget.BudgetItem
import com.budgetapp.budgetapp.domain.model.savedbudget.SavedBudgets

interface BudgetRepository {

    suspend fun addBudgetItem(budgetItem: BudgetItem)

    fun getAllBudgetItems(): LiveData<SavedBudgets>
}