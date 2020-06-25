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

class ScoreTallyView : ConstraintLayout {

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)

    init {
        View.inflate(context, R.layout.compound_view_score_tally_item, this)
    }

    var listener: InteractionListener? = null

    interface InteractionListener {
        fun onDecrement()

        fun onIncrement()
    }

    private fun resetView() {
        gameNameTextView.text = ""
        scoreTextView.text = ""
    }

    fun setScore(gameName: String, score: Int = 0, listener: InteractionListener) {

        resetView()

        gameNameTextView.text = gameName
        scoreTextView.text = score.toString()

        decrementButton.setOnClickListener { listener.onDecrement() }
        incrementButton.setOnClickListener { listener.onIncrement() }
    }

}