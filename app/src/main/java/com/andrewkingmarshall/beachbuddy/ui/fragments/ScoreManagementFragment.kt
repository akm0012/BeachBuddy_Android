package com.andrewkingmarshall.beachbuddy.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.andrewkingmarshall.beachbuddy.R
import com.andrewkingmarshall.beachbuddy.database.realmObjects.Score
import com.andrewkingmarshall.beachbuddy.database.realmObjects.User
import com.andrewkingmarshall.beachbuddy.extensions.toast
import com.andrewkingmarshall.beachbuddy.ui.ItemOffsetDecoration
import com.andrewkingmarshall.beachbuddy.ui.flexible.ManageScoreFlexibleAdapter
import com.andrewkingmarshall.beachbuddy.ui.flexible.ManageUserScoreFlexibleItem
import com.andrewkingmarshall.beachbuddy.viewmodels.ScoreManagementAndroidViewModel
import eu.davidea.flexibleadapter.items.IFlexible
import kotlinx.android.synthetic.main.fragment_score_management.*


class ScoreManagementFragment : Fragment() {

    lateinit var viewModel: ScoreManagementAndroidViewModel

    private var adapter: ManageScoreFlexibleAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel =
            ViewModelProvider(requireActivity()).get(ScoreManagementAndroidViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_score_management, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.showToast.observe(viewLifecycleOwner, Observer { it.toast(requireContext()) })

        viewModel.getUsers().observe(viewLifecycleOwner, Observer { setScoreBoard(it) })

        addGameButton.setOnClickListener {
            "Show dialog".toast(requireContext())
        }


    }

    private fun setScoreBoard(users: List<User>) {

        val flexibleItemList = ArrayList<IFlexible<*>>()

        for (user in users) {
            flexibleItemList.add(ManageUserScoreFlexibleItem(user))
        }

        if (adapter == null) {
            adapter = ManageScoreFlexibleAdapter(
                flexibleItemList,
                object : ManageScoreFlexibleAdapter.InteractionListener {
                    override fun onScoreIncremented(score: Score) {
                        viewModel.onScoreIncremented(score)
                    }

                    override fun onScoreDecremented(score: Score) {
                        viewModel.onScoreDecremented(score)
                    }
                },
                true
            )

            manageUserScoreRecyclerView.adapter = adapter
            manageUserScoreRecyclerView.layoutManager = GridLayoutManager(context, 3)

            if (manageUserScoreRecyclerView.itemDecorationCount == 0) {
                val itemDecoration =
                    ItemOffsetDecoration(requireContext(), R.dimen.grid_item_offset)
                manageUserScoreRecyclerView.addItemDecoration(itemDecoration)
            }

        } else {
            adapter?.updateDataSet(flexibleItemList, true)
        }

    }

}
