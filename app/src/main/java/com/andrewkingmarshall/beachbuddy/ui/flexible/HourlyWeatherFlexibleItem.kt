package com.andrewkingmarshall.beachbuddy.ui.flexible

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.andrewkingmarshall.beachbuddy.R
import com.andrewkingmarshall.beachbuddy.database.realmObjects.CurrentWeather
import com.andrewkingmarshall.beachbuddy.database.realmObjects.HourlyWeatherInfo
import com.andrewkingmarshall.beachbuddy.enums.BeachConditionItemType
import com.andrewkingmarshall.beachbuddy.ui.views.BeachConditionItemView
import com.andrewkingmarshall.beachbuddy.ui.views.HourlyWeatherItemView
import com.andrewkingmarshall.beachbuddy.ui.views.LeaderBoardItemView
import com.andrewkingmarshall.beachbuddy.ui.views.viewmodels.BeachConditionItemViewModel
import com.andrewkingmarshall.beachbuddy.ui.views.viewmodels.HourlyWeatherItemViewModel
import com.andrewkingmarshall.beachbuddy.ui.views.viewmodels.LeaderBoardItemViewModel
import eu.davidea.flexibleadapter.FlexibleAdapter
import eu.davidea.flexibleadapter.items.AbstractFlexibleItem
import eu.davidea.flexibleadapter.items.IFlexible
import eu.davidea.viewholders.FlexibleViewHolder

class HourlyWeatherFlexibleItem(
    private var hourlyWeather: HourlyWeatherInfo
) :
    AbstractFlexibleItem<HourlyWeatherFlexibleItem.BeachConditionItemViewHolder>() {

    override fun equals(other: Any?): Boolean {
        return if (other is HourlyWeatherFlexibleItem) {
            this.hourlyWeather.id == other.hourlyWeather.id
        } else false
    }

    override fun hashCode(): Int {
        return hourlyWeather.hashCode()
    }

    override fun getLayoutRes(): Int {
        return R.layout.container_hourly_weather_item
    }

    override fun createViewHolder(
        view: View?, adapter: FlexibleAdapter<IFlexible<RecyclerView.ViewHolder>>?
    ): BeachConditionItemViewHolder {
        return BeachConditionItemViewHolder(view, adapter as FlexibleAdapter<*>)

    }

    override fun bindViewHolder(
        adapter: FlexibleAdapter<IFlexible<RecyclerView.ViewHolder>>?,
        holder: BeachConditionItemViewHolder?,
        position: Int,
        callbacks: MutableList<Any>?
    ) {

        holder?.hourlyWeatherItemView?.setViewModel(HourlyWeatherItemViewModel(hourlyWeather))
    }

    inner class BeachConditionItemViewHolder(view: View?, adapter: FlexibleAdapter<*>) :
        FlexibleViewHolder(view, adapter) {

        var hourlyWeatherItemView: HourlyWeatherItemView = view as HourlyWeatherItemView
    }
}