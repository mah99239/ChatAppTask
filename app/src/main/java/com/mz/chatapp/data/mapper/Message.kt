package com.mz.chatapp.data.mapper

import com.mz.chatapp.data.db.entity.MessageEntity
import com.mz.chatapp.domain.model.Message

fun MessageEntity.asExternalModel() =
    Message(this.senderName,this.senderId, this.text, this.receiverId, this.sentAt)

fun Message.asDatabaseModel() = MessageEntity(
    senderName = this.senderName,
    senderId = this.senderId,
    text = this.text,
    receiverId = this.receiveId,
    sentAt = this.sentAt,
    isSent = false
)