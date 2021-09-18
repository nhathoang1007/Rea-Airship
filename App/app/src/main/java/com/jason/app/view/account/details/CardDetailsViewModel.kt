package com.jason.app.view.account.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.jason.app.base.viewmodel.BaseViewModel
import com.jason.app.extensions.observeOnUiThread
import com.jason.app.model.Chart
import com.jason.app.repository.chart.IChartRepository
import com.jason.app.utils.observer.addTo

class CardDetailsViewModel constructor(private val repository: IChartRepository) : BaseViewModel() {

    private val _chartObs = MutableLiveData<Pair<MutableList<Chart>, Double>>()
    val chartObs: LiveData<Pair<MutableList<Chart>, Double>>
        get() = _chartObs

    private val _chartFocusedObs = MutableLiveData<Chart>()
    val chartFocusedObs: LiveData<Chart>
        get() = _chartFocusedObs

    init {
        this.getChartData()
    }

    private fun getChartData() {
        repository.getCharts()
            .observeOnUiThread()
            .map { list ->
                _chartFocusedObs.postValue(list.find { it.isFocused })
                val maxValue = list.maxOfOrNull { it.getMaxValue() } ?: 0.0
                Pair(list, maxValue)
            }
            .subscribe({
                _chartObs.postValue(it)
            }, {}).addTo(compositeDisposableBag)

    }

    fun onFocusChartChanged(chart: Chart) {
        _chartFocusedObs.postValue(chart)
        val value = _chartObs.value
        val list = value?.first ?: mutableListOf()
        val maxValue = value?.second ?: 0.0
        list.forEach {
            it.isFocused = it.time == chart.time
        }
        _chartObs.postValue(Pair(list, maxValue))
    }
}

