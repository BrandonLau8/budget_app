package com.budgetapp.budgetapp.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.budgetapp.budgetapp.data.dao.BudgetDao
import com.budgetapp.budgetapp.domain.model.savedbudget.BudgetItem

@Database(entities = [BudgetItem::class], version = 1, exportSchema = false)
abstract class BudgetDatabase : RoomDatabase() {
    abstract fun budgetDao(): BudgetDao


}