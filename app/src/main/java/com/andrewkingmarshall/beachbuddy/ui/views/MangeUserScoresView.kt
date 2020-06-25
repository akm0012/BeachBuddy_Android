package com.andrewkingmarshall.beachbuddy.ui.views

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import com.andrewkingmarshall.beachbuddy.R
import com.andrewkingmarshall.beachbuddy.ui.loadCircleProfilePhoto
import com.andrewkingmarshall.beachbuddy.ui.views.viewmodels.LeaderBoardItemViewModel
import kotlinx.android.synthetic.main.compound_view_leader_board_item.view.*
import kotlinx.android.synthetic.main.compound_view_score_tally_item.view.*

// Todo: build the view that holds the User info at the top and all their scores.
// This will go into the fragment R.V.
class MangeUserScoresView : ConstraintLayout {

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)

    init {
        View.inflate(context, R.layout.compound_view_score_tally_item, this)
    }

    var listener: InteractionListener? = null

    interface InteractionListener {

    }

    private fun resetView() {
    }

    fun setScore(gameName: String, score: Int = 0, listener: InteractionListener) {

        resetView()

    }

}