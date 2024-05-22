package com.mz.chatapp.domain.repo

import com.mz.chatapp.data.db.MessageDao
import com.mz.chatapp.data.mapper.asDatabaseModel
import com.mz.chatapp.data.mapper.asExternalModel
import com.mz.chatapp.data.db.entity.MessageEntity
import com.mz.chatapp.data.service.WebSocketClient
import com.mz.chatapp.di.AppDispatchers
import com.mz.chatapp.di.ApplicationScope
import com.mz.chatapp.di.Dispatcher
import com.mz.chatapp.domain.model.Message
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import timber.log.Timber
import javax.inject.Inject

class ChatRepositoryImpl @Inject constructor(
    @ApplicationScope private val coroutineScope: CoroutineScope,
    @Dispatcher(AppDispatchers.IO) private val io: CoroutineDispatcher,
    private val webSocketClient: WebSocketClient,
    private val messageDao: MessageDao,
) : ChatRepository, WebSocketClient.SocketMessageListener {
    override var userName: String = ""

    init {
        webSocketClient.setListener(this)

    }
   override suspend fun connect(){
       withContext(io) {
           webSocketClient.connect()
       }
    }

    override fun getMessages(): Flow<List<Message>> {
        return messageDao.getMessages().map { messages ->
            messages.map {
                it.asExternalModel()
            }
        }.flowOn(io)
    }

    override suspend fun sendMessage(message: String, userName: String) {
        withContext(io) {
            val isSent = webSocketClient.sendMessage(
                Json.encodeToString(Message(senderName = userName, text = message))
            )
            val messageEntity = MessageEntity(
                senderName = userName,
                text = message,
                sentAt = System.currentTimeMillis(),
                isSent = isSent
            )
            messageDao.insertMessage(messageEntity)
        }

    }

    override fun disconnect() {
        webSocketClient.disconnect()
    }

    override fun onMessage(text: String) {
        coroutineScope.launch {
            try {

                val message = Json.decodeFromString<Message>(text)
                if (userName == message.senderName) return@launch
                val entity = message.asDatabaseModel()
                messageDao.insertMessage(entity)

            } catch (e: Exception) {
                Timber.e("onMessageEX: $e \n and text: $text")
            }

        }
    }

}