package com.andrewkingmarshall.beachbuddy.ui.flexible

import com.andrewkingmarshall.beachbuddy.database.realmObjects.RequestedItem
import eu.davidea.flexibleadapter.FlexibleAdapter
import eu.davidea.flexibleadapter.items.IFlexible

class RequestedItemFlexibleAdapter : FlexibleAdapter<IFlexible<*>> {

    var listener: InteractionListener? = null
        private set

    interface InteractionListener {
        fun onRequestedItemChecked(requestedItem: RequestedItem)
    }

    constructor(items: List<IFlexible<*>>?) : super(items) {}

    constructor(items: List<IFlexible<*>>?, listener: InteractionListener?) : super(items, listener) {
        this.listener = listener
    }

    constructor(items: List<IFlexible<*>>?, listener: InteractionListener?, stableIds: Boolean) : super(
        items,
        listener,
        stableIds
    ) {
        this.listener = listener
    }
}
