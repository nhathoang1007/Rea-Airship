package com.jason.app.view.account.details

import com.jason.app.view.account.BaseAccountView
import com.jason.app.view.account.CardState

interface CardDetailsView: BaseAccountView<CardDetailsViewModel> {

    val chartAdapter: MoneyChartAdapter

    override val notifyToState: CardState
        get() = CardState.MANAGEMENT
}