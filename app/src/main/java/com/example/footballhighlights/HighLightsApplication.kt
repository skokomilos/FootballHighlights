package com.example.footballhighlights

import android.app.Application
import com.example.footballhighlights.data.network.ConnectivityInterceptor
import com.example.footballhighlights.data.network.ConnectivityInterceptorImpl
import com.example.footballhighlights.data.network.HighLightsNetworkDataSource
import com.example.footballhighlights.data.network.HighLightsNetworkDataSourceImpl
import com.example.footballhighlights.data.repository.Repository
import com.example.footballhighlights.data.repository.RepositoryImpl
import com.example.footballhighlights.screens.all.ViewModelFactory
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton

class HighLightsApplication: Application(), KodeinAware {
    override val kodein= Kodein.lazy{
        import(androidXModule(this@HighLightsApplication))

        bind<ConnectivityInterceptor>() with singleton {  ConnectivityInterceptorImpl(instance())}

        bind() from singleton { ApiService(instance()) }

        bind<HighLightsNetworkDataSource>() with singleton { HighLightsNetworkDataSourceImpl(instance()) }

        bind<Repository>() with singleton { RepositoryImpl(instance()) }

        bind() from provider {ViewModelFactory(instance())}
    }
}