package com.jason.app.view.account.manage

import android.view.LayoutInflater
import android.view.ViewGroup
import com.jason.app.base.adapter.BaseBindingAdapter
import com.jason.app.databinding.ViewSettingsOptionBinding
import com.jason.app.model.Option

class AccountSettingAdapter : BaseBindingAdapter<ViewSettingsOptionBinding, Option>() {
    override fun getViewBinding(
        parent: ViewGroup,
        viewType: Int
    ) = ViewSettingsOptionBinding.inflate(LayoutInflater.from(parent.context), parent, false)

    override fun bindViewHolder(dataBinding: ViewSettingsOptionBinding, position: Int) {
        val data = list[position]
        dataBinding.apply {
            option = data
            executePendingBindings()
            isShowBreakLine = if (position == list.lastIndex) {
                false
            } else {
                list[position + 1].header.isNullOrEmpty()
            }
        }
    }
}