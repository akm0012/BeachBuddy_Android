package com.andrewkingmarshall.beachbuddy.ui.views

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.andrewkingmarshall.beachbuddy.R
import com.andrewkingmarshall.beachbuddy.database.realmObjects.Score
import com.andrewkingmarshall.beachbuddy.database.realmObjects.User
import com.andrewkingmarshall.beachbuddy.ui.VerticalSpaceItemDecoration
import com.andrewkingmarshall.beachbuddy.ui.flexible.ManageScoreFlexibleAdapter
import com.andrewkingmarshall.beachbuddy.ui.flexible.ScoreTallyViewFlexibleItem
import com.andrewkingmarshall.beachbuddy.ui.loadCircleProfilePhoto
import eu.davidea.flexibleadapter.items.IFlexible
import kotlinx.android.synthetic.main.compound_view_leader_board_item.view.nameTextView
import kotlinx.android.synthetic.main.compound_view_leader_board_item.view.profileImageView
import kotlinx.android.synthetic.main.compound_view_manage_user_scores_item.view.*

class ManageUserScoresView : ConstraintLayout {

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)

    private var adapter: ManageScoreFlexibleAdapter? = null

    init {
        View.inflate(context, R.layout.compound_view_manage_user_scores_item, this)
    }

    var listener: InteractionListener? = null

    interface InteractionListener {
        fun onScoreIncremented(score: Score)

        fun onScoreDecremented(score: Score)
    }

    private fun resetView() {

        nameTextView.text = ""
        profileImageView.background = null
    }

    fun setUser(user: User, listener: InteractionListener) {

        resetView()

        nameTextView.text = user.firstName

        val profilePhoto = "${context.getString(R.string.base_endpoint)}${user.photoUrl}"
        loadCircleProfilePhoto(context, profilePhoto, profileImageView)

        val flexibleItemList = ArrayList<IFlexible<*>>()

        for (score in user.scores) {
            flexibleItemList.add(ScoreTallyViewFlexibleItem(score))
        }

        if (adapter == null) {
            adapter = ManageScoreFlexibleAdapter(
                flexibleItemList,
                object : ManageScoreFlexibleAdapter.InteractionListener {
                    override fun onScoreIncremented(score: Score) {
                        listener.onScoreIncremented(score)
                    }

                    override fun onScoreDecremented(score: Score) {
                        listener.onScoreDecremented(score)
                    }
                },
                true
            )

            userScoreRecyclerView.adapter = adapter
            userScoreRecyclerView.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)

            // Only add decorations once
            if (userScoreRecyclerView.itemDecorationCount == 0) {
                userScoreRecyclerView.addItemDecoration(
                    VerticalSpaceItemDecoration(
                        resources.getDimension(R.dimen.leader_board_item_space).toInt(), true
                    )
                )
            }

        } else {
            adapter?.updateDataSet(flexibleItemList, true)
        }

    }

}