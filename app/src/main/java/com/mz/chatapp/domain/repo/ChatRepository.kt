package com.mz.chatapp.domain.repo

import com.mz.chatapp.domain.model.Message
import kotlinx.coroutines.flow.Flow


interface ChatRepository {
     var userName: String
    suspend fun connect()

     fun getMessages(): Flow<List<Message>>
    suspend fun sendMessage(message: String, userName: String)
    fun disconnect()
}