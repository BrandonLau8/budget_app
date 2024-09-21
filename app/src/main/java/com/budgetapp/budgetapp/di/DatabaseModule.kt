package com.budgetapp.budgetapp.di

import android.content.Context
import androidx.room.Room
import com.budgetapp.budgetapp.data.dao.BudgetDao
import com.budgetapp.budgetapp.data.database.BudgetDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext appContext: Context): BudgetDatabase {
        return Room.databaseBuilder(
            appContext,
            BudgetDatabase::class.java,
            "budget_database"
        ).build()
    }

    @Provides
    fun provideBudgetDao(database: BudgetDatabase): BudgetDao{
        return database.budgetDao()
    }
}