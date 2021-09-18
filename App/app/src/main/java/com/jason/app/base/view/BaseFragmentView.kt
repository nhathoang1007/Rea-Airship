package com.jason.app.base.view

import com.jason.app.base.viewmodel.BaseViewModel

interface BaseFragmentView<T: BaseViewModel>: BaseView<T> {
    fun getParentView(): BaseActivityView<*>
}