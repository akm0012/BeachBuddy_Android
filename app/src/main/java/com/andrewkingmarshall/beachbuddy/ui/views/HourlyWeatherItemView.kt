package com.andrewkingmarshall.beachbuddy.ui.views

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import com.andrewkingmarshall.beachbuddy.R
import com.andrewkingmarshall.beachbuddy.ui.loadImage
import com.andrewkingmarshall.beachbuddy.ui.views.viewmodels.HourlyWeatherItemViewModel
import kotlinx.android.synthetic.main.compound_view_hourly_weather_item.view.*

class HourlyWeatherItemView : ConstraintLayout {

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)

    init {
        View.inflate(context, R.layout.compound_view_hourly_weather_item, this)
    }

    private fun resetView() {
        hourTimeTextView.text = ""
        hourIconImageView.setImageDrawable(null)
        summaryTextView.text = ""
        feelsLikeTextView.text = ""
        humidityTextView.text = ""
    }

    fun setViewModel(viewModel: HourlyWeatherItemViewModel) {
        resetView()

        hourTimeTextView.text = viewModel.getTime()
        summaryTextView.text = viewModel.getSummary()
        feelsLikeTextView.text = viewModel.getFeelsLike()
        humidityTextView.text = viewModel.getHumidity()

        loadImage(context, viewModel.getIconUrl(), hourIconImageView)
    }

}