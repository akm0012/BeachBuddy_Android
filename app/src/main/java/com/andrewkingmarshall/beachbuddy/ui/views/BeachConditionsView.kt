package com.andrewkingmarshall.beachbuddy.ui.views

import android.content.Context
import android.os.Build
import android.util.AttributeSet
import android.view.View
import android.view.ViewTreeObserver
import android.widget.FrameLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.andrewkingmarshall.beachbuddy.R
import com.andrewkingmarshall.beachbuddy.database.realmObjects.CurrentWeather
import com.andrewkingmarshall.beachbuddy.enums.BeachConditionItemType
import com.andrewkingmarshall.beachbuddy.ui.flexible.BeachConditionFlexibleItem
import eu.davidea.flexibleadapter.FlexibleAdapter
import eu.davidea.flexibleadapter.items.IFlexible
import kotlinx.android.synthetic.main.compound_view_beach_conditions.view.*


class BeachConditionsView : FrameLayout {

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)

    private var adapter: FlexibleAdapter<IFlexible<*>>? = null

    var viewWidth: Int = 0

    var viewHeight: Int = 0

    var currentWeather: CurrentWeather? = null

    init {
        View.inflate(context, R.layout.compound_view_beach_conditions, this)

        viewTreeObserver.addOnGlobalLayoutListener(object :
            ViewTreeObserver.OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                viewTreeObserver.removeOnGlobalLayoutListener(this)

                viewWidth = measuredWidth
                viewHeight = measuredHeight - 20 // Magic number to make it not scroll on my tablet

                currentWeather?.let { showViews(it) }
            }
        })
    }

    fun setWeather(currentWeather: CurrentWeather) {
        this.currentWeather = currentWeather

        if (viewHeight != 0) {
            showViews(currentWeather)
        }
    }

    private fun showViews(currentWeather: CurrentWeather) {

        val flexibleItemList = ArrayList<IFlexible<*>>()

        val rowHeight = viewHeight / enumValues<BeachConditionItemType>().size

        // Iterate through all the Enum Values
        enumValues<BeachConditionItemType>().forEach { beachConditionType ->
            flexibleItemList.add(
                BeachConditionFlexibleItem(
                    beachConditionType,
                    currentWeather,
                    rowHeight
                )
            )
        }

        if (adapter == null) {

            adapter = FlexibleAdapter(flexibleItemList)

            beachConditionsRecyclerView.adapter = adapter
            beachConditionsRecyclerView.layoutManager =
                LinearLayoutManager(context, RecyclerView.VERTICAL, false)

        } else {
            adapter?.updateDataSet(flexibleItemList, true)
        }
    }
}