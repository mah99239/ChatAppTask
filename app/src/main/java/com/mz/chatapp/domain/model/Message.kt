package com.mz.chatapp.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class Message(
    val senderName: String,
    val senderId: Long? = null,
    val text: String,
    val receiveId: Long? = null,
    val sentAt: Long = System.currentTimeMillis()
)