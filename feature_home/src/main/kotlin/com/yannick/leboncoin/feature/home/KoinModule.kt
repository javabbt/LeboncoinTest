package com.yannick.leboncoin.feature.home

import com.yannick.leboncoin.feature.home.data.dataModule
import com.yannick.leboncoin.feature.home.domain.domainModules
import com.yannick.leboncoin.feature.home.presentation.presentationModule

val featureHomeModules = listOf(
    presentationModule,
    dataModule,
    domainModules,
)
