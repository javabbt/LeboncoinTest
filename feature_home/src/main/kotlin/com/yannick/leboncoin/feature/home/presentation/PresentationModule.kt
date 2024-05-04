package com.yannick.leboncoin.feature.home.presentation

import androidx.lifecycle.SavedStateHandle
import com.yannick.leboncoin.feature.home.presentation.screen.home.HomeScreenViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

internal val presentationModule = module {
    viewModel { (handle: SavedStateHandle) ->
        HomeScreenViewModel(handle, get(), get())
    }
}
