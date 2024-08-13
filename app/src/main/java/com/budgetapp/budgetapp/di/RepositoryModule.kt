package com.budgetapp.budgetapp.di

import com.budgetapp.budgetapp.data.LinkTokenRepositoryImpl
import com.budgetapp.budgetapp.domain.respository.LinkTokenRepository
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
    abstract fun bindLinkTokenRepository(impl: LinkTokenRepositoryImpl): LinkTokenRepository
}