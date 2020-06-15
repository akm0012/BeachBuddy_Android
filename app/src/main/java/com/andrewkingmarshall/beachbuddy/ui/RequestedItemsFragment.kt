package com.andrewkingmarshall.beachbuddy.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.andrewkingmarshall.beachbuddy.R
import com.andrewkingmarshall.beachbuddy.inject.Injector
import com.andrewkingmarshall.beachbuddy.job.GetNotCompletedRequestedItemsJob
import com.birbit.android.jobqueue.JobManager
import javax.inject.Inject

/**
 * This fragment will show all the Requested Items that people want brought out to the beach.
 */
class RequestedItemsFragment : Fragment() {

    @Inject
    lateinit var jobManager: JobManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_requested_items, container, false)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Injector.obtain().inject(this)

        jobManager.addJobInBackground(GetNotCompletedRequestedItemsJob())
    }


}
