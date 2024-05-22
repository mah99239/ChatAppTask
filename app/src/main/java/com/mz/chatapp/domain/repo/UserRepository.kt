package com.mz.chatapp.domain.repo

import com.mz.chatapp.domain.model.User
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    suspend fun getUser(): User
    fun getUsers(): Flow<List<User>>
}