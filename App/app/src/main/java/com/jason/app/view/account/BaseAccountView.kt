package com.jason.app.view.account

import android.view.View
import android.view.ViewGroup
import android.widget.ScrollView
import androidx.core.widget.NestedScrollView
import androidx.recyclerview.widget.RecyclerView
import com.jason.app.base.view.BaseFragmentView
import com.jason.app.base.viewmodel.BaseViewModel
import com.jason.app.customize.view.scroll.JsScrollView

interface BaseAccountView<T: BaseViewModel>: BaseFragmentView<T> {
    val notifyToState: CardState
    fun onPageSelected()
    fun onPageUnSelected()
    fun onPagerFlyingChanged(isFlying: Boolean)
    fun initScrollHeight(height: Int)
    fun onScrollTo(y: Int)
    fun getScroller(): JsScrollView
    fun getListView(): RecyclerView
    fun getAddingSpace(): View
    fun getContainerSpacingView(): View
    fun getContainer(): ViewGroup
}