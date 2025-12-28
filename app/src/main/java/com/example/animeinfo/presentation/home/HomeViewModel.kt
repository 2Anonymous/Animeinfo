package com.example.animeinfo.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.animeinfo.domain.usecase.GetTopAnimeUseCase
import com.example.animeinfo.util.NetworkConnectivityObserver
import com.example.animeinfo.util.NetworkStatus
import com.example.animeinfo.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getTopAnimeUseCase: GetTopAnimeUseCase,
    private val networkConnectivityObserver: NetworkConnectivityObserver
) : ViewModel() {

    private val _state = MutableStateFlow(HomeState())
    val state: StateFlow<HomeState> = _state.asStateFlow()

    private val _networkStatus = MutableStateFlow(NetworkStatus.Available)
    val networkStatus: StateFlow<NetworkStatus> = _networkStatus.asStateFlow()

    init {
        observeNetworkStatus()
        onEvent(HomeEvent.LoadAnime)
    }

    fun onEvent(event: HomeEvent) {
        when (event) {
            is HomeEvent.LoadAnime -> loadTopAnime()
            is HomeEvent.LoadMoreAnime -> loadMore()
            is HomeEvent.Refresh -> refresh()
            is HomeEvent.ClearError -> clearError()
        }
    }

    private fun observeNetworkStatus() {
        networkConnectivityObserver.observe()
            .onEach { status ->
                _networkStatus.value = status
                _state.update { it.copy(isOffline = status != NetworkStatus.Available) }

                if (status == NetworkStatus.Available && _state.value.animeList.isEmpty()) {
                    onEvent(HomeEvent.Refresh)
                }
            }
            .launchIn(viewModelScope)
    }

    private fun loadTopAnime(forceRefresh: Boolean = false) {
        if (_state.value.isLoading) return

        viewModelScope.launch {
            val page = if (forceRefresh) 1 else _state.value.currentPage

            getTopAnimeUseCase(page = page, forceRefresh = forceRefresh)
                .collect { result ->
                    when (result) {
                        is Resource.Loading -> {
                            _state.update { it.copy(isLoading = true, error = null) }
                        }
                        is Resource.Success -> {
                            val newList = if (forceRefresh || page == 1) {
                                result.data ?: emptyList()
                            } else {
                                _state.value.animeList + (result.data ?: emptyList())
                            }
                            _state.update {
                                it.copy(
                                    animeList = newList,
                                    isLoading = false,
                                    isRefreshing = false,
                                    error = null,
                                    currentPage = if (forceRefresh) 1 else page,
                                    hasMorePages = (result.data?.size ?: 0) >= 25
                                )
                            }
                        }
                        is Resource.Error -> {
                            _state.update {
                                it.copy(
                                    animeList = result.data ?: it.animeList,
                                    isLoading = false,
                                    isRefreshing = false,
                                    error = result.message
                                )
                            }
                        }
                    }
                }
        }
    }

    private fun loadMore() {
        if (_state.value.isLoading || _state.value.isLoadingMore || !_state.value.hasMorePages) return

        viewModelScope.launch {
            val nextPage = _state.value.currentPage + 1

            getTopAnimeUseCase(page = nextPage, forceRefresh = false)
                .collect { result ->
                    when (result) {
                        is Resource.Loading -> {
                            _state.update { it.copy(isLoadingMore = true) }
                        }
                        is Resource.Success -> {
                            val newData = result.data ?: emptyList()
                            _state.update {
                                it.copy(
                                    animeList = it.animeList + newData,
                                    isLoadingMore = false,
                                    currentPage = nextPage,
                                    hasMorePages = newData.size >= 25
                                )
                            }
                        }
                        is Resource.Error -> {
                            _state.update {
                                it.copy(
                                    isLoadingMore = false,
                                    error = result.message
                                )
                            }
                        }
                    }
                }
        }
    }

    private fun refresh() {
        _state.update { it.copy(isRefreshing = true) }
        loadTopAnime(forceRefresh = true)
    }

    private fun clearError() {
        _state.update { it.copy(error = null) }
    }
}
