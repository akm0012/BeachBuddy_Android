package com.andrewkingmarshall.beachbuddy.ui.views

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.annotation.DrawableRes
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import com.andrewkingmarshall.beachbuddy.R
import com.andrewkingmarshall.beachbuddy.ui.views.viewmodels.BeachConditionItemViewModel
import kotlinx.android.synthetic.main.compound_view_beach_condition_item.view.*

class BeachConditionItemView : ConstraintLayout {

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)

    init {
        View.inflate(context, R.layout.compound_view_beach_condition_item, this)
    }

    private fun resetView() {
        iconImageView.setImageDrawable(null)
        titleTextView.text = ""
        contentTextView.text = ""
        bottomDivider.visibility = View.GONE
    }

    fun setHeight(height: Int) {
        val params = layoutParams
        params.height = height
        layoutParams = params
    }

    fun setViewModel(viewModel: BeachConditionItemViewModel) {
        resetView()
        iconImageView.setImageDrawable(ContextCompat.getDrawable(context, viewModel.getIconDrawable()))
        titleTextView.text = viewModel.getTitle()
        contentTextView.text = viewModel.getContent()

    }

    fun setBottomDividerVisibility(isVisible: Boolean) {
        if (isVisible) {
            bottomDivider.visibility = View.VISIBLE
        } else {
            bottomDivider.visibility = View.GONE
        }
    }
}