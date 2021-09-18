package com.jason.app.view.account.manage

import com.jason.app.view.account.BaseAccountView
import com.jason.app.view.account.CardState

interface CardManagementView: BaseAccountView<CardManagementViewModel> {
    val settingAdapter: AccountSettingAdapter

    override val notifyToState: CardState
        get() = CardState.DETAILS
}