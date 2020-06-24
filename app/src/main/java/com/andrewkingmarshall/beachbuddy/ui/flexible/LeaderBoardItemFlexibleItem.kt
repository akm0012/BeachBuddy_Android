package com.andrewkingmarshall.beachbuddy.ui.flexible

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.andrewkingmarshall.beachbuddy.R
import com.andrewkingmarshall.beachbuddy.database.realmObjects.User
import com.andrewkingmarshall.beachbuddy.ui.views.LeaderBoardItemView
import com.andrewkingmarshall.beachbuddy.ui.views.viewmodels.LeaderBoardItemViewModel
import eu.davidea.flexibleadapter.FlexibleAdapter
import eu.davidea.flexibleadapter.items.AbstractFlexibleItem
import eu.davidea.flexibleadapter.items.IFlexible
import eu.davidea.viewholders.FlexibleViewHolder

class LeaderBoardItemFlexibleItem(var user: User) :
    AbstractFlexibleItem<LeaderBoardItemFlexibleItem.LeaderBoardItemViewHolder>() {

    override fun equals(other: Any?): Boolean {
        return if (other is LeaderBoardItemFlexibleItem) {
            this.user.id == other.user.id
        } else false
    }

    override fun hashCode(): Int {
        return user.hashCode()
    }

    override fun getLayoutRes(): Int {
        return R.layout.container_leader_board_item_view
    }

    override fun createViewHolder(
        view: View?, adapter: FlexibleAdapter<IFlexible<RecyclerView.ViewHolder>>?
    ): LeaderBoardItemViewHolder {
        return LeaderBoardItemViewHolder(view, adapter as LeaderBoardFlexibleAdapter)

    }

    override fun bindViewHolder(
        adapter: FlexibleAdapter<IFlexible<RecyclerView.ViewHolder>>?,
        holder: LeaderBoardItemViewHolder?,
        position: Int,
        callbacks: MutableList<Any>?
    ) {

        holder?.leaderBoardItemView?.setViewModel(LeaderBoardItemViewModel(user))

        holder?.leaderBoardItemView?.setOnClickListener {
            holder.listener?.onLeaderBoardItemClicked(user)
        }
    }

    inner class LeaderBoardItemViewHolder(view: View?, adapter: LeaderBoardFlexibleAdapter) :
        FlexibleViewHolder(view, adapter) {

        var leaderBoardItemView: LeaderBoardItemView = view as LeaderBoardItemView

        var listener: LeaderBoardFlexibleAdapter.InteractionListener? = adapter.listener
    }
}