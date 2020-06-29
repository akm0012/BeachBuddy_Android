package com.andrewkingmarshall.beachbuddy.ui.views

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import com.andrewkingmarshall.beachbuddy.R
import com.andrewkingmarshall.beachbuddy.ui.loadImage
import com.andrewkingmarshall.beachbuddy.ui.views.viewmodels.DailyWeatherItemViewModel
import kotlinx.android.synthetic.main.compound_view_daily_weather_item.view.*

class DailyWeatherItemView : ConstraintLayout {

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)

    init {
        View.inflate(context, R.layout.compound_view_daily_weather_item, this)
    }

    private fun resetView() {
        dayTimeTextView.text = ""
        dailyIconImageView.setImageDrawable(null)
        summaryTextView.text = ""
        feelsLikeTextView.text = ""
        rainAmountTextView.text = ""
    }

    fun setViewModel(viewModel: DailyWeatherItemViewModel) {
        resetView()

        dayTimeTextView.text = viewModel.getTime()
        summaryTextView.text = viewModel.getSummary()
        feelsLikeTextView.text = viewModel.getFeelsLike()
        rainAmountTextView.text = viewModel.getRain()

        loadImage(context, viewModel.getIconUrl(), dailyIconImageView)
    }

}