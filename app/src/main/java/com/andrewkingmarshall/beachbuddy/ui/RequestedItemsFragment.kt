package com.andrewkingmarshall.beachbuddy.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.andrewkingmarshall.beachbuddy.R

/**
 * This fragment will show all the Requested Items that people want brought out to the beach.
 */
class RequestedItemsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_requested_items, container, false)
    }


}
