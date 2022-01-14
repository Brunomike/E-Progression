package com.example.e_progression.presentation.main.news_detail

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.e_progression.common.Constants
import com.example.e_progression.common.Resource
import com.example.e_progression.domain.use_cases.GetNewsItemUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject


@HiltViewModel
class NewsItemViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val getNewsItemUseCase: GetNewsItemUseCase
) : ViewModel() {

    private val _state = mutableStateOf(NewsItemListState())
    val state: State<NewsItemListState> = _state

    init {
        savedStateHandle.get<String>(Constants.PARAM_NEWS_ID)?.let { newsId ->
            getNewsById(newsId)
        }
    }

    private fun getNewsById(newsId: String) {
        getNewsItemUseCase(newsId).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _state.value = NewsItemListState(news = result.data)
                }
                is Resource.Error -> {
                    _state.value =
                        NewsItemListState(error = result.message ?: "An unexpected error occured")
                }
                is Resource.Loading -> {
                    _state.value = NewsItemListState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }
}