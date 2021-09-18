package com.jason.app.repository.chart

import com.jason.app.model.Chart
import io.reactivex.Single

interface IChartRepository {
    fun getCharts(): Single<MutableList<Chart>>
}