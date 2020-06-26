package com.andrewkingmarshall.beachbuddy.ui.flexible

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.andrewkingmarshall.beachbuddy.R
import com.andrewkingmarshall.beachbuddy.database.realmObjects.Score
import com.andrewkingmarshall.beachbuddy.database.realmObjects.User
import com.andrewkingmarshall.beachbuddy.ui.views.ManageUserScoresView
import com.andrewkingmarshall.beachbuddy.ui.views.viewmodels.LeaderBoardItemViewModel
import eu.davidea.flexibleadapter.FlexibleAdapter
import eu.davidea.flexibleadapter.items.AbstractFlexibleItem
import eu.davidea.flexibleadapter.items.IFlexible
import eu.davidea.viewholders.FlexibleViewHolder

class ManageUserScoreFlexibleItem(var user: User) :
    AbstractFlexibleItem<ManageUserScoreFlexibleItem.ManageUserScoreViewHolder>() {

    override fun equals(other: Any?): Boolean {
        return if (other is ManageUserScoreFlexibleItem) {
            this.user.id == other.user.id
        } else false
    }

    override fun hashCode(): Int {
        return user.hashCode()
    }

    override fun getLayoutRes(): Int {
        return R.layout.container_manage_user_score
    }

    override fun createViewHolder(
        view: View?, adapter: FlexibleAdapter<IFlexible<RecyclerView.ViewHolder>>?
    ): ManageUserScoreViewHolder {
        return ManageUserScoreViewHolder(view, adapter as ManageScoreFlexibleAdapter)

    }

    override fun bindViewHolder(
        adapter: FlexibleAdapter<IFlexible<RecyclerView.ViewHolder>>?,
        holder: ManageUserScoreViewHolder?,
        position: Int,
        callbacks: MutableList<Any>?
    ) {

        holder?.manageUserScoresView?.setUser(user, object : ManageUserScoresView.InteractionListener {
            override fun onScoreIncremented(score: Score) {
                holder.listener?.onScoreIncremented(score)
            }

            override fun onScoreDecremented(score: Score) {
                holder.listener?.onScoreDecremented(score)
            }
        })
    }

    inner class ManageUserScoreViewHolder(view: View?, adapter: ManageScoreFlexibleAdapter) :
        FlexibleViewHolder(view, adapter) {

        var manageUserScoresView: ManageUserScoresView = view as ManageUserScoresView

        var listener: ManageScoreFlexibleAdapter.InteractionListener? = adapter.listener
    }
}