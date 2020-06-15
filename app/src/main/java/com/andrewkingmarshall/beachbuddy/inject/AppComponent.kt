package com.andrewkingmarshall.beachbuddy.inject

import com.andrewkingmarshall.beachbuddy.job.GetNotCompletedRequestedItemsJob
import com.andrewkingmarshall.beachbuddy.ui.MainActivity
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [ApplicationModule::class])
interface AppComponent {

    fun inject(job: GetNotCompletedRequestedItemsJob)

}
