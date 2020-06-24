package com.andrewkingmarshall.beachbuddy.ui.fragments

import android.media.MediaPlayer
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.andrewkingmarshall.beachbuddy.R
import com.andrewkingmarshall.beachbuddy.database.realmObjects.RequestedItem
import com.andrewkingmarshall.beachbuddy.extensions.toast
import com.andrewkingmarshall.beachbuddy.ui.flexible.RequestedItemEmptyStateFlexibleItem
import com.andrewkingmarshall.beachbuddy.ui.flexible.RequestedItemFlexibleAdapter
import com.andrewkingmarshall.beachbuddy.ui.flexible.RequestedItemFlexibleItem
import com.andrewkingmarshall.beachbuddy.ui.views.CompletedItemsHeaderView
import com.andrewkingmarshall.beachbuddy.viewmodels.RequestedItemAndroidViewModel
import eu.davidea.flexibleadapter.items.IFlexible
import kotlinx.android.synthetic.main.fragment_requested_items.*


/**
 * This fragment will show all the Requested Items that people want brought out to the beach.
 */
class RequestedItemsFragment : Fragment() {

    lateinit var viewModel: RequestedItemAndroidViewModel

    private var requestedItems: List<RequestedItem> = ArrayList()
    private var completedItems: List<RequestedItem> = ArrayList()

    private var adapter = RequestedItemFlexibleAdapter(
        null,
        object : RequestedItemFlexibleAdapter.InteractionListener {
            override fun onRequestedItemChecked(requestedItem: RequestedItem) {
                viewModel.onRequestedItemChecked(requestedItem)
            }

        },
        true
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel =
            ViewModelProvider(requireActivity()).get(RequestedItemAndroidViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_requested_items, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getRequestedItems()
            .observe(viewLifecycleOwner, Observer { setRequestedItems(it) })

        viewModel.getAllRequestedItemsThatWereCompletedToday()
            .observe(viewLifecycleOwner, Observer { setCompletedItems(it) })

        viewModel.showToast
            .observe(viewLifecycleOwner, Observer { it.toast(requireContext()) })

        setUpSwipeToRefresh()

        setUpRecyclerView()
    }

    private fun setUpSwipeToRefresh() {
        swipeRefreshLayout.setColorSchemeColors(
            ContextCompat.getColor(requireContext(), R.color.colorPrimary),
            ContextCompat.getColor(requireContext(), R.color.colorAccent)
        )
        swipeRefreshLayout.setOnRefreshListener { viewModel.onPullToRefresh() }
        viewModel.showLoadingEvent.observe(
            viewLifecycleOwner,
            Observer { swipeRefreshLayout.isRefreshing = it })
    }

    private fun setUpRecyclerView() {
        recyclerView.adapter = this.adapter
        recyclerView.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
    }

    private fun setRequestedItems(requestedItems: List<RequestedItem>) {
        this.requestedItems = requestedItems
        setBothRequestedAndCompletedItems(requestedItems, completedItems)
    }

    private fun setCompletedItems(completedItems: List<RequestedItem>) {
        this.completedItems = completedItems
        setBothRequestedAndCompletedItems(requestedItems, completedItems)
    }

    private fun setBothRequestedAndCompletedItems(requestedItems: List<RequestedItem>,
                                                  completedItems: List<RequestedItem>) {

        val flexibleItemsList = ArrayList<IFlexible<*>>()

        requestedItems.forEach {
            flexibleItemsList.add(RequestedItemFlexibleItem(it, null))
        }

        val headerView = CompletedItemsHeaderView(requestedItems.isEmpty())

        completedItems.forEach {
            flexibleItemsList.add(RequestedItemFlexibleItem(it, headerView))
        }

        if (requestedItems.isEmpty() && completedItems.isEmpty()) {
            flexibleItemsList.add(RequestedItemEmptyStateFlexibleItem())
        }

        adapter.updateDataSet(flexibleItemsList)
    }


}
