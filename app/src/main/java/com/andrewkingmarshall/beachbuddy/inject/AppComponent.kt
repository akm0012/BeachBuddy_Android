package com.andrewkingmarshall.beachbuddy.inject

import com.andrewkingmarshall.beachbuddy.MyFirebaseMessagingService
import com.andrewkingmarshall.beachbuddy.job.GetDashboardJob
import com.andrewkingmarshall.beachbuddy.job.GetNotCompletedRequestedItemsJob
import com.andrewkingmarshall.beachbuddy.job.PostCompleteRequestedItemJob
import com.andrewkingmarshall.beachbuddy.repository.DashboardRepository
import com.andrewkingmarshall.beachbuddy.repository.FirebaseRepository
import com.andrewkingmarshall.beachbuddy.repository.RequestedItemRepository
import com.andrewkingmarshall.beachbuddy.ui.fragments.RequestedItemsFragment
import com.andrewkingmarshall.beachbuddy.ui.views.viewmodels.LeaderBoardItemViewModel
import com.andrewkingmarshall.beachbuddy.ui.views.viewmodels.RequestedItemViewModel
import com.andrewkingmarshall.beachbuddy.viewmodels.DashboardAndroidViewModel
import com.andrewkingmarshall.beachbuddy.viewmodels.MainActivityAndroidViewModel
import com.andrewkingmarshall.beachbuddy.viewmodels.RequestedItemAndroidViewModel
import com.andrewkingmarshall.beachbuddy.viewmodels.ScoreManagementAndroidViewModel
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [ApplicationModule::class])
interface AppComponent {

    fun inject(job: GetNotCompletedRequestedItemsJob)
    fun inject(job: PostCompleteRequestedItemJob)
    fun inject(job: GetDashboardJob)

    fun inject(pokoViewModel: LeaderBoardItemViewModel)
    fun inject(pokoViewModel: RequestedItemViewModel)

    fun inject(androidViewModel: RequestedItemAndroidViewModel)
    fun inject(androidViewModel: MainActivityAndroidViewModel)
    fun inject(androidViewModel: DashboardAndroidViewModel)
    fun inject(androidViewModel: ScoreManagementAndroidViewModel)

    fun inject(repository: RequestedItemRepository)
    fun inject(repository: FirebaseRepository)
    fun inject(repository: DashboardRepository)

    fun inject(service: MyFirebaseMessagingService)

}
