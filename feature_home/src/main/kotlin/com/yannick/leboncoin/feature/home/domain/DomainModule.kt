package com.yannick.leboncoin.feature.home.domain

import com.yannick.leboncoin.feature.home.domain.usecases.GetAlbumsUseCase
import org.koin.dsl.module

internal val domainModules = module {
    single {
        GetAlbumsUseCase(get())
    }
}
