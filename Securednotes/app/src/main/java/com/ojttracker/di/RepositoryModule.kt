package com.ojttracker.di

import com.ojttracker.data.database.dao.TimeRecordDao
import com.ojttracker.data.repository.TimeRecordRepository
import com.ojttracker.ui.dashboard.DashboardViewModelFactory
import com.ojttracker.ui.history.HistoryViewModelFactory
import com.ojttracker.ui.settings.SettingsViewModelFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideTimeRecordRepository(
        timeRecordDao: TimeRecordDao
    ): TimeRecordRepository {
        return TimeRecordRepository(timeRecordDao)
    }

    @Provides
    fun provideDashboardViewModelFactory(
        repository: TimeRecordRepository
    ): DashboardViewModelFactory {
        return DashboardViewModelFactory(repository)
    }
    
    @Provides
    fun provideHistoryViewModelFactory(
        repository: TimeRecordRepository
    ): HistoryViewModelFactory {
        return HistoryViewModelFactory(repository)
    }
    
    @Provides
    fun provideSettingsViewModelFactory(
        repository: TimeRecordRepository
    ): SettingsViewModelFactory {
        return SettingsViewModelFactory(repository)
    }
}
