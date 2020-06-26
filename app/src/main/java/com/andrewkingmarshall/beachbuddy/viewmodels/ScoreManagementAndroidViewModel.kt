package com.andrewkingmarshall.beachbuddy.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.andrewkingmarshall.beachbuddy.database.realmObjects.Score
import com.andrewkingmarshall.beachbuddy.database.realmObjects.User
import com.andrewkingmarshall.beachbuddy.eventbus.GetDashboardEvent
import com.andrewkingmarshall.beachbuddy.eventbus.PostUpdateScoreEvent
import com.andrewkingmarshall.beachbuddy.inject.Injector
import com.andrewkingmarshall.beachbuddy.network.NetworkCallStatusListener
import com.andrewkingmarshall.beachbuddy.repository.DashboardRepository
import com.andrewkingmarshall.beachbuddy.repository.ScoreRepository
import io.realm.Realm
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import timber.log.Timber
import javax.inject.Inject

class ScoreManagementAndroidViewModel(application: Application) : AndroidViewModel(application) {

    val showToast: SingleLiveEvent<String> = SingleLiveEvent()
    val showLoadingEvent: SingleLiveEvent<Boolean> = SingleLiveEvent()

    private val realm = Realm.getDefaultInstance()

    @Inject
    lateinit var dashboardRepository: DashboardRepository

    @Inject
    lateinit var scoreRepository: ScoreRepository

    init {
        Injector.obtain().inject(this)
        EventBus.getDefault().register(this)
    }

    fun getUsers(): LiveData<List<User>> {
        return dashboardRepository.getUsers(realm)
    }

    fun onAddNewGame(gameName: String) {
        scoreRepository.addGame(gameName, object : NetworkCallStatusListener<Void>() {

            override fun onErrorOccurred(error: Throwable?) {
                showToast.value = error?.localizedMessage
            }
        })
    }

    fun onScoreIncremented(score: Score) {
        scoreRepository.updateScore(score.id, score.winCount + 1)
    }

    fun onScoreDecremented(score: Score) {
        val winCount = score.winCount - 1

        if (winCount < 0) {
            Timber.d("Score is already 0. Not updating.")
            return
        }

        scoreRepository.updateScore(score.id, winCount)
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onPostUpdateScoreEvent(event: PostUpdateScoreEvent) {
        if (event.isDone || event.error != null) {
            showLoadingEvent.value = false
        }

        if (event.error != null) {
            showToast.value = event.error.localizedMessage
        }
    }

    override fun onCleared() {
        super.onCleared()
        realm.close()
        EventBus.getDefault().unregister(this)
    }
}