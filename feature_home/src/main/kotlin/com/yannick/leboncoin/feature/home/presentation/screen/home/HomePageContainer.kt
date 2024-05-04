package com.yannick.leboncoin.feature.home.presentation.screen.home

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import org.koin.androidx.compose.koinViewModel

@Composable
fun HomePageContainer() {
    val viewModel: HomeScreenViewModel = koinViewModel()
    val uiState by viewModel.uiState.collectAsState()
    val context = LocalContext.current
    LaunchedEffect(key1 = viewModel) {
        viewModel.sideEffects.onEach { sideEffect ->
            when (sideEffect) {
                is SideEffect.ShowSearchToast -> {
                    Toast.makeText(
                        context,
                        sideEffect.msg,
                        Toast.LENGTH_SHORT,
                    ).show()
                }

                is SideEffect.ShowError -> {
                    Toast.makeText(
                        context,
                        sideEffect.msg,
                        Toast.LENGTH_SHORT,
                    ).show()
                }

                is SideEffect.ShowUnexpectedError -> {
                    Toast.makeText(
                        context,
                        sideEffect.msg,
                        Toast.LENGTH_SHORT,
                    ).show()
                }
            }
        }.collect()
    }
    HomeScreen(
        modifier = Modifier,
        onQueryChanged = {
            viewModel.setQuery(it)
        },
        uiState = uiState,
        onRefresh = {
            viewModel.onSearch()
        },
    )
}
