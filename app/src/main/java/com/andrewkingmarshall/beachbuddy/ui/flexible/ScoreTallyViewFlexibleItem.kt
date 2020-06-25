package com.andrewkingmarshall.beachbuddy.ui.flexible

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.andrewkingmarshall.beachbuddy.R
import com.andrewkingmarshall.beachbuddy.database.realmObjects.Score
import com.andrewkingmarshall.beachbuddy.ui.views.ScoreTallyView
import com.andrewkingmarshall.beachbuddy.ui.views.viewmodels.LeaderBoardItemViewModel
import eu.davidea.flexibleadapter.FlexibleAdapter
import eu.davidea.flexibleadapter.items.AbstractFlexibleItem
import eu.davidea.flexibleadapter.items.IFlexible
import eu.davidea.viewholders.FlexibleViewHolder

class ScoreTallyViewFlexibleItem(var score: Score) :
    AbstractFlexibleItem<ScoreTallyViewFlexibleItem.ScoreTallyItemViewHolder>() {

    override fun equals(other: Any?): Boolean {
        return if (other is ScoreTallyViewFlexibleItem) {
            this.score.id == other.score.id
        } else false
    }

    override fun hashCode(): Int {
        return score.hashCode()
    }

    override fun getLayoutRes(): Int {
        return R.layout.container_score_tally_item_view
    }

    override fun createViewHolder(
        view: View?, adapter: FlexibleAdapter<IFlexible<RecyclerView.ViewHolder>>?
    ): ScoreTallyItemViewHolder {
        return ScoreTallyItemViewHolder(view, adapter as ManageScoreFlexibleAdapter)

    }

    override fun bindViewHolder(
        adapter: FlexibleAdapter<IFlexible<RecyclerView.ViewHolder>>?,
        holder: ScoreTallyItemViewHolder?,
        position: Int,
        callbacks: MutableList<Any>?
    ) {

        holder?.scoreTallyView?.setScore(score.name, score.winCount, object : ScoreTallyView.InteractionListener {
            override fun onDecrement() {
                holder.listener?.onScoreDecremented(score)
            }

            override fun onIncrement() {
                holder.listener?.onScoreIncremented(score)
            }
        })
    }

    inner class ScoreTallyItemViewHolder(view: View?, adapter: ManageScoreFlexibleAdapter) :
        FlexibleViewHolder(view, adapter) {

        var scoreTallyView: ScoreTallyView = view as ScoreTallyView

        var listener: ManageScoreFlexibleAdapter.InteractionListener? = adapter.listener
    }
}