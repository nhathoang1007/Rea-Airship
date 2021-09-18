package com.jason.app.repository.chart

import com.jason.app.model.Chart
import io.reactivex.Single

class ChartRepository: IChartRepository {
    override fun getCharts(): Single<MutableList<Chart>> {
        val list = mutableListOf<Chart>()
        list.add(Chart(time = "2021-01-01", moneyIn = 1000.0, moneyOut = 500.0))
        list.add(Chart(time = "2021-02-01", moneyIn = 500.0, moneyOut = 1000.0))
        list.add(Chart(time = "2021-03-01", moneyIn = 700.0, moneyOut = 500.0))
        list.add(Chart(time = "2021-04-01", moneyIn = 1400.0, moneyOut = 230.0).apply {
            isFocused = true
        })
        return Single.just(list)
    }
}