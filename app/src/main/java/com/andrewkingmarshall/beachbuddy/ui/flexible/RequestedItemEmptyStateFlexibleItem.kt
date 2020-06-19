package com.andrewkingmarshall.beachbuddy.ui.flexible

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.andrewkingmarshall.beachbuddy.R
import com.andrewkingmarshall.beachbuddy.database.realmObjects.RequestedItem
import com.andrewkingmarshall.beachbuddy.ui.views.RequestedItemView
import com.andrewkingmarshall.beachbuddy.ui.views.viewmodels.RequestedItemViewModel
import eu.davidea.flexibleadapter.FlexibleAdapter
import eu.davidea.flexibleadapter.items.AbstractSectionableItem
import eu.davidea.flexibleadapter.items.IFlexible
import eu.davidea.flexibleadapter.items.IHeader
import eu.davidea.viewholders.FlexibleViewHolder

class RequestedItemEmptyStateFlexibleItem():
    AbstractSectionableItem<RequestedItemEmptyStateFlexibleItem.RequestedItemViewHolder, IHeader<*>>(null) {

    override fun equals(other: Any?): Boolean {
        return true
    }

    override fun hashCode(): Int {
        return 1
    }

    override fun getLayoutRes(): Int {
        return R.layout.compound_view_completed_items_empty_state
    }

    override fun createViewHolder(
        view: View?, adapter: FlexibleAdapter<IFlexible<RecyclerView.ViewHolder>>?
    ): RequestedItemViewHolder {
        return RequestedItemViewHolder(view, adapter as RequestedItemFlexibleAdapter)

    }

    override fun bindViewHolder(
        adapter: FlexibleAdapter<IFlexible<RecyclerView.ViewHolder>>?,
        holder: RequestedItemViewHolder?,
        position: Int,
        callbacks: MutableList<Any>?
    ) {

    }

    inner class RequestedItemViewHolder(view: View?, adapter: RequestedItemFlexibleAdapter) :
        FlexibleViewHolder(view, adapter) {
    }
}