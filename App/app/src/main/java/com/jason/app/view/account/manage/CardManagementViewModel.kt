package com.jason.app.view.account.manage

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.jason.app.base.viewmodel.BaseViewModel
import com.jason.app.extensions.observeOnUiThread
import com.jason.app.model.Option
import com.jason.app.repository.resource.IResourceRepository
import com.jason.app.utils.observer.addTo

class CardManagementViewModel(private val repository: IResourceRepository) : BaseViewModel() {

    private val _settingOptionObs = MutableLiveData<MutableList<Option>>()
    val settingOptionObs: LiveData<MutableList<Option>>
        get() = _settingOptionObs

    init {
        this.initSettingOptions()
    }

    private fun initSettingOptions() {
        repository.getCardSettingOptions()
            .observeOnUiThread()
            .flatMap { list ->
                repository.getAccountSettingOptions()
                    .observeOnUiThread()
                    .map {
                        list.addAll(it)
                        list
                    }
            }.subscribe({
                _settingOptionObs.postValue(it)
            }, {}).addTo(compositeDisposableBag)
    }
}

