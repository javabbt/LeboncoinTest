package com.yannick.leboncoin.feature.home.presentation.screen.home

import androidx.annotation.StringRes
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yannick.leboncoin.base.utils.CoroutinesDispatcherProvider
import com.yannick.leboncoin.feature.home.domain.model.AlbumUiModel
import com.yannick.leboncoin.feature.home.domain.usecases.GetAlbumsUseCase
import com.yannick.leboncoin.feature.home.domain.utils.Result
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomeScreenViewModel(
    private val savedStateHandle: SavedStateHandle,
    private val getAlbumsUseCase: GetAlbumsUseCase,
    private val coroutinesDispatcherProvider: CoroutinesDispatcherProvider,
) : ViewModel() {

    private val _uiState = MutableStateFlow(UiState())
    val uiState = _uiState.asStateFlow()

    private val _sideEffects = MutableSharedFlow<SideEffect>()
    val sideEffects = _sideEffects.asSharedFlow()

    private val flow = savedStateHandle.getStateFlow("query", "")
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(),
            initialValue = "",
        )

    init {
        onSearch()
        viewModelScope.launch {
            combine(uiState, flow) { state, query ->
                state.copy(query = query)
            }.stateIn(
                scope = viewModelScope
            ).collectLatest {
                onQuery(it.query)
            }
        }
    }

    private suspend fun filterAlbums(query: String): List<AlbumUiModel> {
        return withContext(coroutinesDispatcherProvider.io) {
            if (query.isEmpty())
                return@withContext _uiState.value.albums
            _uiState.value.albums.filter { album ->
                album.title.contains(query, ignoreCase = true)
            }
        }
    }

    private fun onQuery(query: String) {
        viewModelScope.launch {
            val filteredAlbums = filterAlbums(query)
            _uiState.update {
                it.copy(filteredAlbums = filteredAlbums)
            }
        }
    }

    fun setQuery(query: String) {
        savedStateHandle["query"] = query
    }

    fun onSearch() {
        viewModelScope.launch(coroutinesDispatcherProvider.io) {
            _uiState.update {
                it.copy(isLoading = true)
            }
            when (val result = getAlbumsUseCase()) {
                is Result.Success -> {
                    _uiState.update {
                        it.copy(
                            albums = result.output,
                            filteredAlbums = result.output,
                            isLoading = false,
                        )
                    }
                }

                is Result.Error -> {
                    sendSideEffect(SideEffect.ShowError(result.exception))
                }

                is Result.UnexpectedError -> {
                    sendSideEffect(SideEffect.ShowUnexpectedError(result.exception))
                }
            }
            _uiState.update {
                it.copy(
                    isLoading = false,
                )
            }
        }
    }

    private fun sendSideEffect(sideEffect: SideEffect) {
        viewModelScope.launch(coroutinesDispatcherProvider.io) {
            _sideEffects.emit(sideEffect)
        }
    }
}

sealed class SideEffect {
    class ShowError(val msg: String) : SideEffect()
    class ShowUnexpectedError(@StringRes val msg: Int) : SideEffect()
    class ShowSearchToast(@StringRes val msg: Int) : SideEffect()
}

data class UiState(
    val isLoading: Boolean = true,
    val albums: List<AlbumUiModel> = emptyList(),
    val filteredAlbums: List<AlbumUiModel> = emptyList(),
    val query: String = "",
)
