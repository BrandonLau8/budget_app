package com.budgetapp.budgetapp.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.budgetapp.budgetapp.data.dao.BudgetDao
import com.budgetapp.budgetapp.domain.model.savedbudget.BudgetItem

@Database(entities = [BudgetItem::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun budgetDao(): BudgetDao
}