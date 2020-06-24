package com.andrewkingmarshall.beachbuddy.ui.views.viewmodels

import android.content.Context
import com.andrewkingmarshall.beachbuddy.R
import com.andrewkingmarshall.beachbuddy.database.realmObjects.Score
import com.andrewkingmarshall.beachbuddy.database.realmObjects.User
import com.andrewkingmarshall.beachbuddy.inject.Injector
import javax.inject.Inject

class LeaderBoardItemViewModel(var user : User) {

    @Inject
    lateinit var context: Context

    init {
        Injector.obtain().inject(this)
    }

    fun getName() : String = user.firstName

    fun getSubtitle() : String {

        var highScore = Score()

        for (score in user.scores) {
            if (score.winCount > highScore.winCount) {
                highScore = score
            }
        }

        return "Best Game: ${highScore.name} (${highScore.winCount})"
    }

    fun getScore() : String {

        var totalWinCount = 0

        for (score in user.scores) {
            totalWinCount += score.winCount
        }

        return "$totalWinCount"
    }

    fun getProfilePhotoUrl(): String {
        return "${context.getString(R.string.base_endpoint)}${user.photoUrl}"
    }

}