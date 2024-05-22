package com.mz.chatapp.di

import com.mz.chatapp.domain.repo.ChatRepository
import com.mz.chatapp.domain.repo.ChatRepositoryImpl
import com.mz.chatapp.domain.repo.UserRepository
import com.mz.chatapp.domain.repo.UserRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

/**
 * A Dagger module that provides bindings for the repository.
 */
@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {
    @Binds
    fun bindChatRepositoryImpl(chatRepositoryImpl: ChatRepositoryImpl): ChatRepository

    @Binds
    fun bindUsersRepositoryImpl(userRepositoryImpl: UserRepositoryImpl): UserRepository
}