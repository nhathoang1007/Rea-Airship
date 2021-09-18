package com.jason.app.repository.resource

import com.jason.app.model.Option
import io.reactivex.Single

interface IResourceRepository {
    fun getCardSettingOptions(): Single<MutableList<Option>>
    fun getAccountSettingOptions(): Single<MutableList<Option>>
}