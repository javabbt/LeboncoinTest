package com.yannick.leboncoin.app

import android.app.Application
import com.yannick.leboncoin.BuildConfig
import com.yannick.leboncoin.appModule
import com.yannick.leboncoin.base.baseModule
import com.yannick.leboncoin.feature.home.featureHomeModules
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext
import timber.log.Timber

class LeboncoinApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        initKoin()
        initTimber()
    }

    private fun initKoin() {
        GlobalContext.startKoin {
            androidLogger()
            androidContext(this@LeboncoinApplication)
            modules(appModule)
            modules(baseModule)
            modules(featureHomeModules)
        }
    }

    private fun initTimber() {
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}
