package com.jason.app.base.view

import android.content.Context
import com.jason.app.base.viewmodel.BaseViewModel

interface BaseView<VM: BaseViewModel> {
    val mContext: Context
    val mViewModel: VM
    fun handleError(error: Throwable?)
    fun handleLoading(isLoading: Boolean?)
}