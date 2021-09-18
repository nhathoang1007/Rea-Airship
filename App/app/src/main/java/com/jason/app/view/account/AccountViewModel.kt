package com.jason.app.view.account

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.jason.app.base.view.BaseFragment
import com.jason.app.base.viewmodel.BaseViewModel
import com.jason.app.view.account.details.CardDetailsFragment
import com.jason.app.view.account.manage.CardManagementFragment

class AccountViewModel : BaseViewModel() {

    private val _currentStateObs = MutableLiveData<CardState>()
    val currentStateObs: LiveData<CardState>
        get() = _currentStateObs

    private val _fragmentsObs = MutableLiveData<MutableList<BaseAccountFragment<*, *>>>()
    val fragmentsObs: LiveData<MutableList<BaseAccountFragment<*, *>>>
        get() = _fragmentsObs

    init {
        this.initFragmentPager()
    }

    private fun initFragmentPager() {
        mutableListOf<BaseAccountFragment<*, *>>(
            CardDetailsFragment(),
            CardManagementFragment()
        ).apply {
            _fragmentsObs.postValue(this)
        }
    }

    fun onStateChanged(position: Int) {
        _currentStateObs.postValue(CardState.getByValue(position, CardState.DETAILS))
    }
}

