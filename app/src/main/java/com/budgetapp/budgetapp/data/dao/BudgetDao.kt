package com.budgetapp.budgetapp.data.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.budgetapp.budgetapp.domain.model.savedbudget.BudgetItem
import com.budgetapp.budgetapp.domain.model.savedbudget.SavedBudgets

@Dao
interface BudgetDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun addBudgetItem(budgetItem: BudgetItem)

    // It allows UI components (like Activities and Fragments) to observe data changes and automatically
    // update the UI when the underlying data changes, without requiring manual updates.
    @Query("SELECT * FROM budgetitem")
    fun getAllBudgetItems(): LiveData<SavedBudgets>
}