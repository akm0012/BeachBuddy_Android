package com.andrewkingmarshall.beachbuddy.ui.views

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import com.andrewkingmarshall.beachbuddy.R
import kotlinx.android.synthetic.main.compound_view_leader_board_item.view.*

class LeaderBoardItemView : ConstraintLayout {

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)

    init {
        View.inflate(context, R.layout.compound_view_leader_board_item, this)

    }

    private fun resetView() {
        nameTextView.text = ""
        totalScoreTextView.text = ""
        subtitleTextView.text = ""
        profileImageView.setImageDrawable(null)
    }



}