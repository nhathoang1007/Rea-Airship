package com.jason.app.di

import com.jason.app.view.account.AccountViewModel
import com.jason.app.view.account.details.CardDetailsViewModel
import com.jason.app.view.account.manage.CardManagementViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { AccountViewModel() }
    viewModel { CardDetailsViewModel(get()) }
    viewModel { CardManagementViewModel(get()) }
}