package com.jason.app.repository.resource

import android.content.Context
import com.jason.app.MyApp
import com.jason.app.R
import com.jason.app.extensions.getString
import com.jason.app.model.Option
import io.reactivex.Single

class ResourceRepository constructor(private val context: Context): IResourceRepository {

    override fun getCardSettingOptions(): Single<MutableList<Option>> {
        val list = R.array.card_settings.parseOptions(R.string.card_settings_text, true)
        return Single.just(list)
    }

    override fun getAccountSettingOptions(): Single<MutableList<Option>> {
        val list = R.array.account_settings.parseOptions(R.string.account_settings_text)
        return Single.just(list)
    }

    private fun Int.parseOptions(header: Int, isFirstOption: Boolean = false): MutableList<Option> {
        val list = mutableListOf<Option>()
        MyApp.instance.resources.getStringArray(this).forEachIndexed { index, s ->
            val option = Option(name = s).apply {
                if (index == 0) {
                    this.header = header.getString()
                    this.isFirstOption = isFirstOption
                }
            }
            list.add(option)
        }
        return list
    }
}