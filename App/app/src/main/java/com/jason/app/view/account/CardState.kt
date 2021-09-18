package com.jason.app.view.account

import com.jason.app.utils.EnumCompanion

enum class CardState(val position: Int) {
    DETAILS(0),
    MANAGEMENT(1);

    companion object : EnumCompanion<Int, CardState>(values().associateBy(CardState::position))
}