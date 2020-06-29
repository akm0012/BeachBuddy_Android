package com.andrewkingmarshall.beachbuddy.ui.views

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.andrewkingmarshall.beachbuddy.R
import com.andrewkingmarshall.beachbuddy.database.realmObjects.HourlyWeatherInfo
import com.andrewkingmarshall.beachbuddy.ui.flexible.HourlyWeatherFlexibleItem
import eu.davidea.flexibleadapter.FlexibleAdapter
import eu.davidea.flexibleadapter.items.IFlexible
import kotlinx.android.synthetic.main.compound_view_hourly_weather.view.*

class HourlyWeatherView : FrameLayout {

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)

    private var adapter: FlexibleAdapter<IFlexible<*>>? = null

    init {
        View.inflate(context, R.layout.compound_view_hourly_weather, this)
    }


    fun setWeather(hourlyWeatherInfoList: List<HourlyWeatherInfo>) {

        val flexibleItemList = ArrayList<IFlexible<*>>()

        hourlyWeatherInfoList.forEach {
            flexibleItemList.add(HourlyWeatherFlexibleItem(it))
        }

        if (adapter == null) {

            adapter = FlexibleAdapter(flexibleItemList)

            hourlyWeatherRecyclerView.adapter = adapter
            hourlyWeatherRecyclerView.layoutManager =
                LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)

        } else {
            adapter?.updateDataSet(flexibleItemList, true)
            adapter?.smoothScrollToPosition(0)
        }
    }
}