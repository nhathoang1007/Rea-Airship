package com.jason.app.view.account

import com.jason.app.base.adapter.BasePagerAdapter
import com.jason.app.base.view.BaseActivityView
import com.jason.app.base.view.BaseView

/**
 * Created by Nhat Vo on 14/06/2021.
 */
interface AccountView: BaseActivityView<AccountViewModel> {
    val fragmentAdapter: BasePagerAdapter<BaseAccountFragment<*, *>>
    fun navigateTo(state: CardState)
    fun notifyScrollHeightChanged(height: Int, toState: CardState)
    fun notifyScrolled(scrollY: Int, toState: CardState)
}