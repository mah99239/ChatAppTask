package com.mz.chatapp.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.mz.chatapp.data.db.entity.MessageEntity
import com.mz.chatapp.data.db.entity.UserEntity
/**
 * The Room Database for the application.
 *
 * This class defines the database schema and provides access to the database.
 */
@Database(
    entities = [MessageEntity::class , UserEntity::class],
    version = 1, exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun messageDao(): MessageDao
    abstract fun userDao(): UserDao
}