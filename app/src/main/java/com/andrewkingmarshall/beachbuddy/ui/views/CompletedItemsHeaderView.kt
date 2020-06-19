package com.andrewkingmarshall.beachbuddy.ui.views

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.andrewkingmarshall.beachbuddy.R
import com.andrewkingmarshall.beachbuddy.database.realmObjects.RequestedItem
import com.andrewkingmarshall.beachbuddy.ui.flexible.RequestedItemFlexibleAdapter
import com.andrewkingmarshall.beachbuddy.ui.flexible.RequestedItemFlexibleItem
import com.andrewkingmarshall.beachbuddy.ui.views.viewmodels.RequestedItemViewModel
import eu.davidea.flexibleadapter.FlexibleAdapter
import eu.davidea.flexibleadapter.items.AbstractHeaderItem
import eu.davidea.flexibleadapter.items.IFlexible
import eu.davidea.viewholders.FlexibleViewHolder

class CompletedItemsHeaderView(private val showEmptyState: Boolean) :
    AbstractHeaderItem<CompletedItemsHeaderView.HeaderViewHolder>() {

    override fun equals(other: Any?): Boolean {
        return if (other is CompletedItemsHeaderView) {
            this.showEmptyState == other.showEmptyState
        } else false
    }

    override fun hashCode(): Int {
        return 1
    }

    override fun getLayoutRes(): Int {
        return R.layout.compound_view_completed_items_header
    }

    override fun createViewHolder(
        view: View?, adapter: FlexibleAdapter<IFlexible<RecyclerView.ViewHolder>>?
    ): HeaderViewHolder {
        return HeaderViewHolder(view, adapter as RequestedItemFlexibleAdapter)

    }

    override fun bindViewHolder(
        adapter: FlexibleAdapter<IFlexible<RecyclerView.ViewHolder>>?,
        holder: CompletedItemsHeaderView.HeaderViewHolder?,
        position: Int,
        callbacks: MutableList<Any>?
    ) {
        if (showEmptyState) {
            holder?.emptyStateImageView?.visibility = View.VISIBLE
            holder?.emptyStateTextView?.visibility = View.VISIBLE
        } else {
            holder?.emptyStateImageView?.visibility = View.GONE
            holder?.emptyStateTextView?.visibility = View.GONE
        }
    }

    inner class HeaderViewHolder(view: View?, adapter: RequestedItemFlexibleAdapter) :
        FlexibleViewHolder(view, adapter, false) {

        val emptyStateImageView = view?.findViewById<ImageView>(R.id.emptyStateImageView)
        val emptyStateTextView = view?.findViewById<TextView>(R.id.emptyStateTextView)
    }

}


