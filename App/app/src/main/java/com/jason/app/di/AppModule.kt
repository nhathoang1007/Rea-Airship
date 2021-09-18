package com.jason.app.di

import com.jason.app.repository.chart.ChartRepository
import com.jason.app.repository.chart.IChartRepository
import com.jason.app.repository.resource.IResourceRepository
import com.jason.app.repository.resource.ResourceRepository
import org.koin.dsl.module


val repositoryModule = module {
    single<IResourceRepository> { ResourceRepository(get()) }
    single<IChartRepository> { ChartRepository() }
}