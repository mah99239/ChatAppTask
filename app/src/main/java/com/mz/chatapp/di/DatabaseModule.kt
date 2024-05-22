package com.mz.chatapp.di

import android.content.Context
import androidx.room.Room
import com.mz.chatapp.data.db.AppDatabase
import com.mz.chatapp.data.db.MessageDao
import com.mz.chatapp.data.db.UserDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * A Dagger module that provides dependencies related to the database.
 */
@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    /**
     * Provides an instance of the AppDatabase.
     *
     * @param context The application context.
     * @return An instance of the AppDatabase.
     */
    @Provides
    @Singleton
    fun provideAppDatabase(
        @ApplicationContext context: Context,
    ): AppDatabase = Room.databaseBuilder(
        context,
        AppDatabase::class.java,
        "chat-db",
    ).build()


    @Provides
    @Singleton
    fun provideMessageDao(database: AppDatabase): MessageDao = database.messageDao()

    @Provides
    @Singleton
    fun provideUserDao(database: AppDatabase): UserDao = database.userDao()
}