package com.mz.chatapp.domain.repo

import com.mz.chatapp.data.db.UserDao
import com.mz.chatapp.di.AppDispatchers
import com.mz.chatapp.di.Dispatcher
import com.mz.chatapp.domain.model.User
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    @Dispatcher(AppDispatchers.IO) private val io: CoroutineDispatcher,
    private val userDao: UserDao
): UserRepository {
    override suspend fun getUser(): User {
        return withContext(io) {
            val userEntity = userDao.getUser("")
            User(userEntity?.id, userEntity?.userName!!)
        }
    }
    override fun getUsers(): Flow<List<User>> {
        return userDao.getAllUsers().map { users ->
            users.map {
                User(it.id, it.userName)
            }
        }.flowOn(io)
    }
}