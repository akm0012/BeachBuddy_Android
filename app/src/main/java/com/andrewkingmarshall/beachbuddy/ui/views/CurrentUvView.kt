package com.andrewkingmarshall.beachbuddy.ui.views

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import androidx.core.content.ContextCompat
import com.andrewkingmarshall.beachbuddy.R
import com.andrewkingmarshall.beachbuddy.ui.views.viewmodels.CurrentUvViewModel
import kotlinx.android.synthetic.main.compound_view_current_uv.view.*

class CurrentUvView : FrameLayout {

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)

    private var viewModel: CurrentUvViewModel? = null

    init {
        View.inflate(context, R.layout.compound_view_current_uv, this)
    }

    fun setViewModel(viewModel: CurrentUvViewModel) {

        this.viewModel = viewModel

        // Reset the view (todo: check if this works overnight)
        sunView.setCurrentTime(viewModel.getSunViewStartTime())

        sunView.apply {
            setStartTime(viewModel.getSunViewStartTime())
            setEndTime(viewModel.getSunViewEndTime())
            setCurrentTime(viewModel.getSunViewCurrentTime())
            setArcSolidColor(ContextCompat.getColor(context, viewModel.getUvColor()))
            sunView.invalidate()
        }

        if (safeExposureTimeTextView.visibility == View.VISIBLE) {
            safeExposureTimeTextView.text = viewModel.getTimeToBurn(null)
        }

        uvIndexTextView.text = viewModel.getUvIndex()
    }

    fun showSafeExposureTimeForSkinType(skinType: Int?) {
        safeExposureTimeTextView.text = viewModel?.getTimeToBurn(skinType)

        safeExposureTitle.visibility = View.VISIBLE
        safeExposureTimeTextView.visibility = View.VISIBLE
    }
}