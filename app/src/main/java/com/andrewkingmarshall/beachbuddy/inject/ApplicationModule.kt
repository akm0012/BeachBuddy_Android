package com.andrewkingmarshall.beachbuddy.inject

import android.content.Context
import com.andrewkingmarshall.beachbuddy.BeachBuddyApplication
import com.andrewkingmarshall.beachbuddy.job.BaseJob
import com.andrewkingmarshall.beachbuddy.network.service.ApiService
import com.andrewkingmarshall.beachbuddy.repository.DashboardRepository
import com.andrewkingmarshall.beachbuddy.repository.FirebaseRepository
import com.andrewkingmarshall.beachbuddy.repository.RequestedItemRepository
import com.andrewkingmarshall.beachbuddy.repository.ScoreRepository
import com.birbit.android.jobqueue.Job
import com.birbit.android.jobqueue.JobManager
import com.birbit.android.jobqueue.config.Configuration
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ApplicationModule(
    val application: BeachBuddyApplication
) {

    @Provides
    @Singleton
    fun provideContext(): Context {
        return application
    }

    @Provides
    @Singleton
    fun provideApiService(context: Context): ApiService {
        return ApiService(context)
    }

    @Provides
    @Singleton
    fun provideRequestedItemRepository(): RequestedItemRepository {
        return RequestedItemRepository()
    }

    @Provides
    @Singleton
    fun provideDashboardRepository(): DashboardRepository {
        return DashboardRepository()
    }

    @Provides
    @Singleton
    fun provideFirebaseRepository(): FirebaseRepository {
        return FirebaseRepository()
    }

    @Provides
    @Singleton
    fun provideScoreRepository(): ScoreRepository {
        return ScoreRepository()
    }

    @Provides
    @Singleton
    fun jobManager(): JobManager {
        val maxCount = 5
        val loadFactor = 3
        val minCount = 1
        val configuration =
            Configuration.Builder(application)
                .consumerKeepAlive(45)
                .maxConsumerCount(maxCount)
                .loadFactor(loadFactor)
                .minConsumerCount(minCount)
                .injector { job: Job ->
                    if (job is BaseJob) {
                        job.inject(Injector.obtain())
                    }
                }
                .build()
        return JobManager(configuration)
    }
}
