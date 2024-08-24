package com.budgetapp.budgetapp.di

import com.budgetapp.budgetapp.data.TokenRepositoryImpl
import com.budgetapp.budgetapp.domain.respository.TokenRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    @Singleton
    abstract fun bindLinkTokenRepository(impl: TokenRepositoryImpl): TokenRepository
}