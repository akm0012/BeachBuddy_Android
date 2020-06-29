package com.andrewkingmarshall.beachbuddy.ui.views

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.andrewkingmarshall.beachbuddy.R
import com.andrewkingmarshall.beachbuddy.database.realmObjects.DailyWeatherInfo
import com.andrewkingmarshall.beachbuddy.ui.flexible.DailyWeatherFlexibleItem
import eu.davidea.flexibleadapter.FlexibleAdapter
import eu.davidea.flexibleadapter.items.IFlexible
import kotlinx.android.synthetic.main.compound_view_daily_weather.view.*

class DailyWeatherView : FrameLayout {

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)

    private var adapter: FlexibleAdapter<IFlexible<*>>? = null

    init {
        View.inflate(context, R.layout.compound_view_daily_weather, this)
    }


    fun setWeather(dailyWeatherInfoList: List<DailyWeatherInfo>) {

        val flexibleItemList = ArrayList<IFlexible<*>>()

        dailyWeatherInfoList.forEach {
            flexibleItemList.add(DailyWeatherFlexibleItem(it))
        }

        if (adapter == null) {

            adapter = FlexibleAdapter(flexibleItemList)

            dailyWeatherRecyclerView.adapter = adapter
            dailyWeatherRecyclerView.layoutManager =
                LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)

        } else {
            adapter?.updateDataSet(flexibleItemList, true)
        }
    }
}