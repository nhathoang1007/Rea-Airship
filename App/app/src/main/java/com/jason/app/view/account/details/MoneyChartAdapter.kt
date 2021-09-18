package com.jason.app.view.account.details

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import com.jason.app.base.adapter.BaseBindingAdapter
import com.jason.app.databinding.ViewMoneyChartBinding
import com.jason.app.model.Chart

class MoneyChartAdapter(private val onChartSelected: (Chart) -> Unit): BaseBindingAdapter<ViewMoneyChartBinding, Chart>() {

    private var maxValue: Double = 0.0

    override fun getViewBinding(parent: ViewGroup, viewType: Int): ViewMoneyChartBinding {
       return ViewMoneyChartBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    }

    override fun bindViewHolder(dataBinding: ViewMoneyChartBinding, position: Int) {
        val data = list[position]
        dataBinding.apply {
            chart = data
            moneyInColumn.setHeight(data.inHeight(maxValue))
            moneyOutColumn.setHeight(data.outHeight(maxValue))
            this.onChartClicked = onChartSelected
        }
    }

    fun updateData(list: MutableList<Chart>, maxValue: Double) {
        this.list.apply {
            clear()
            addAll(list)
        }
        this.maxValue = maxValue
        notifyDataSetChanged()
    }

    private fun ImageView.setHeight(height: Int) {
        layoutParams.height = height
        requestLayout()
    }
}