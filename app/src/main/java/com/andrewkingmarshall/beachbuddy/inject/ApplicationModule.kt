package com.andrewkingmarshall.beachbuddy.inject

import android.content.Context
import com.andrewkingmarshall.beachbuddy.BeachBuddyApplication
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
}
