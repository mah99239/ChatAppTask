package com.mz.chatapp.data.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "messages")
data class MessageEntity(
    @PrimaryKey(autoGenerate = true) val id: Long? = null,
    val senderName: String,
    val text: String,
    val receiverId: Long? = null,
    val sentAt: Long,
    val isSent: Boolean,
)