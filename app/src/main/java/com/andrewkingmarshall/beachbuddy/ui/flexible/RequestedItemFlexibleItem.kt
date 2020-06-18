package com.andrewkingmarshall.beachbuddy.ui.flexible

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.andrewkingmarshall.beachbuddy.R
import com.andrewkingmarshall.beachbuddy.database.realmObjects.RequestedItem
import com.andrewkingmarshall.beachbuddy.ui.views.RequestedItemView
import com.andrewkingmarshall.beachbuddy.ui.views.viewmodels.RequestedItemViewModel
import eu.davidea.flexibleadapter.FlexibleAdapter
import eu.davidea.flexibleadapter.items.AbstractFlexibleItem
import eu.davidea.flexibleadapter.items.IFlexible
import eu.davidea.viewholders.FlexibleViewHolder

class RequestedItemFlexibleItem(var requestedItem: RequestedItem):
    AbstractFlexibleItem<RequestedItemFlexibleItem.RequestedItemViewHolder>() {

    override fun equals(other: Any?): Boolean {
        return if (other is RequestedItem) {
            this.requestedItem.id == other.id
        } else false
    }

    override fun hashCode(): Int {
        return requestedItem.hashCode()
    }

    override fun getLayoutRes(): Int {
        return R.layout.container_requested_item_view
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
        holder?.requestedItemView?.setViewModel(RequestedItemViewModel(requestedItem))

        holder?.requestedItemView?.interactionListener = object : RequestedItemView.InteractionListener {
            override fun onCheckboxClicked() {
                holder?.listener?.onRequestedItemChecked(requestedItem)
            }
        }
    }

    inner class RequestedItemViewHolder(view: View?, adapter: RequestedItemFlexibleAdapter) :
        FlexibleViewHolder(view, adapter) {

        var requestedItemView: RequestedItemView = view as RequestedItemView

        var listener: RequestedItemFlexibleAdapter.InteractionListener? = adapter.listener
    }
}