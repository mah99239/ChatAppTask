package com.mz.chatapp.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.mz.chatapp.data.db.entity.MessageEntity
import kotlinx.coroutines.flow.Flow
/**
 * Data Access Object (DAO) interface for the `MessageEntity` class.
 * This interface provides methods for interacting with the database table `messages`.
 */
@Dao
interface MessageDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMessage(message: MessageEntity)

    @Query("SELECT * FROM messages ORDER BY sentAt ASC")
    fun getMessages(): Flow<List<MessageEntity>>
}