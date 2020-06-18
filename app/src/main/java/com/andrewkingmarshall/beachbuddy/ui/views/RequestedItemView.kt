package com.andrewkingmarshall.beachbuddy.ui.views

import android.content.Context
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import com.andrewkingmarshall.beachbuddy.R
import com.andrewkingmarshall.beachbuddy.ui.loadCircleProfilePhoto
import com.andrewkingmarshall.beachbuddy.ui.views.viewmodels.RequestedItemViewModel
import kotlinx.android.synthetic.main.compound_view_requested_item.view.*

class RequestedItemView : FrameLayout {

    var interactionListener:InteractionListener? = null

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)

    interface InteractionListener {
        fun onCheckboxClicked()
    }

    init {
        View.inflate(context, R.layout.compound_view_requested_item, this)

        checkboxImageView.setOnClickListener {
            checkboxImageView.setImageResource(R.drawable.ic_check_circle_grey_24dp)
            checkboxImageView.isEnabled = false
            interactionListener?.onCheckboxClicked()
        }
    }

    private fun resetView() {
        checkboxImageView.setImageResource(R.drawable.ic_empty_check_24dp)
        checkboxImageView.isEnabled = true

        itemTitleTextView.text = ""

        subtitleTextView.text = ""

        profileImageView.setImageDrawable(null)

        itemTitleTextView.paintFlags = itemTitleTextView.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
        subtitleTextView.paintFlags = subtitleTextView.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
    }

    fun setViewModel(viewModel: RequestedItemViewModel) {

        resetView()

        itemTitleTextView.text = viewModel.getTitle()

        subtitleTextView.text = viewModel.getSubTitle()

        if (viewModel.isChecked()) {

            checkboxImageView.isEnabled = false
            checkboxImageView.setImageResource(R.drawable.ic_check_circle_grey_24dp)

            // Strike through the TextViews
            itemTitleTextView.paintFlags = itemTitleTextView.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
            subtitleTextView.paintFlags = subtitleTextView.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG

            loadCircleProfilePhoto(context, viewModel.getProfilePhotoUrl(), profileImageView, true)

        } else {
            loadCircleProfilePhoto(context, viewModel.getProfilePhotoUrl(), profileImageView)
        }
    }

}