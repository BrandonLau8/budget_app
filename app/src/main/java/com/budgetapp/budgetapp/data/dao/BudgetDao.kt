package com.budgetapp.budgetapp.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.budgetapp.budgetapp.domain.model.savedbudget.BudgetItem

@Dao
interface BudgetDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBudgetItem(budgetItem: BudgetItem)

    @Query("SELECT * FROM budgetitem")
    suspend fun getAllBudgetItems(): List<BudgetItem>
}