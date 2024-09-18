package com.budgetapp.budgetapp.di

import com.budgetapp.budgetapp.data.TokenRepositoryImpl
import com.budgetapp.budgetapp.data.TransactionRepositoryImpl
import com.budgetapp.budgetapp.domain.respository.TokenRepository
import com.budgetapp.budgetapp.domain.respository.TransactionRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module //marks class as a dagger module
@InstallIn(SingletonComponent::class) //the provided dependencies will have a singleton scope, lasting the entire lifecycle of the app
abstract class RepositoryModule { //module that provides di for repository classes
    @Binds
    @Singleton
    abstract fun bindLinkTokenRepository(impl: TokenRepositoryImpl): TokenRepository

    @Binds
    @Singleton
    abstract fun bindTransactionRepository(impl: TransactionRepositoryImpl): TransactionRepository
}