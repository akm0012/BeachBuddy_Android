package com.andrewkingmarshall.beachbuddy.inject

import com.andrewkingmarshall.beachbuddy.job.GetNotCompletedRequestedItemsJob
import com.andrewkingmarshall.beachbuddy.job.PostCompleteRequestedItemJob
import com.andrewkingmarshall.beachbuddy.repository.RequestedItemRepository
import com.andrewkingmarshall.beachbuddy.ui.fragments.RequestedItemsFragment
import com.andrewkingmarshall.beachbuddy.ui.views.viewmodels.RequestedItemViewModel
import com.andrewkingmarshall.beachbuddy.viewmodels.RequestedItemAndroidViewModel
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [ApplicationModule::class])
interface AppComponent {

    fun inject(job: GetNotCompletedRequestedItemsJob)
    fun inject(job: PostCompleteRequestedItemJob)

    fun inject(pokoViewModel: RequestedItemViewModel)

    fun inject(androidViewModel: RequestedItemAndroidViewModel)

    fun inject(repository: RequestedItemRepository)

}
