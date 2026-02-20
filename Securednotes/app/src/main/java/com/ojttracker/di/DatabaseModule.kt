package com.ojttracker.di

import android.content.Context
import androidx.room.Room
import com.ojttracker.data.database.AppDatabase
import com.ojttracker.data.database.dao.TimeRecordDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "ojt_tracker_database"
        ).build()
    }

    @Provides
    fun provideTimeRecordDao(database: AppDatabase): TimeRecordDao {
        return database.timeRecordDao()
    }
}
