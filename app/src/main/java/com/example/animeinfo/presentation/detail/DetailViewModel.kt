package com.example.animeinfo.presentation.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.animeinfo.domain.usecase.GetAnimeDetailsUseCase
import com.example.animeinfo.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val getAnimeDetailsUseCase: GetAnimeDetailsUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _state = MutableStateFlow(DetailState())
    val state: StateFlow<DetailState> = _state.asStateFlow()

    private val animeId: Int = checkNotNull(savedStateHandle["animeId"])

    init {
        onEvent(DetailEvent.LoadAnimeDetails(animeId))
    }

    fun onEvent(event: DetailEvent) {
        when (event) {
            is DetailEvent.LoadAnimeDetails -> loadAnimeDetails(event.animeId)
            is DetailEvent.Retry -> loadAnimeDetails(animeId)
            is DetailEvent.ClearError -> clearError()
            is DetailEvent.PlayTrailer -> {}
        }
    }

    private fun loadAnimeDetails(id: Int) {
        viewModelScope.launch {
            getAnimeDetailsUseCase(id)
                .collect { result ->
                    when (result) {
                        is Resource.Loading -> {
                            _state.update { it.copy(isLoading = true, error = null) }
                        }
                        is Resource.Success -> {
                            _state.update {
                                it.copy(
                                    anime = result.data,
                                    isLoading = false,
                                    error = null
                                )
                            }
                        }
                        is Resource.Error -> {
                            _state.update {
                                it.copy(
                                    anime = result.data,
                                    isLoading = false,
                                    error = result.message
                                )
                            }
                        }
                    }
                }
        }
    }

    private fun clearError() {
        _state.update { it.copy(error = null) }
    }
}
