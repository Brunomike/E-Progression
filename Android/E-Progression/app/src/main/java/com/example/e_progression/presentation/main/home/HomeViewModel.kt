package com.example.e_progression.presentation.main.home

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.e_progression.common.Constants
import com.example.e_progression.common.Resource
import com.example.e_progression.domain.use_cases.FeeSummaryUseCase
import com.example.e_progression.domain.use_cases.GetExamTypesUseCase
import com.example.e_progression.domain.use_cases.GetFeesUseCase
import com.example.e_progression.domain.use_cases.LatestNewsUseCase
import com.example.e_progression.presentation.main.fees.FeesState
import com.example.e_progression.presentation.main.news_detail.NewsItemListState
import com.example.e_progression.presentation.main.results.ExamState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val latestNewsUseCase: LatestNewsUseCase,
    private val getExamTypesUseCase: GetExamTypesUseCase,
    private val feeSummaryUseCase: FeeSummaryUseCase,
    private val getFeesUseCase: GetFeesUseCase
) : ViewModel() {
    private val _latestNewsState = mutableStateOf(NewsItemListState())
    val latestNewsState: State<NewsItemListState> = _latestNewsState

    private val _examState = mutableStateOf(ExamState())
    val examState: State<ExamState> = _examState

    private val _feesState = mutableStateOf(FeesState())
    val feesState: State<FeesState> = _feesState


    init {
        getLatestNews()
        getExamTypes()
        getFeeRecords(Constants.PARAM_USER_UUID)

    }

    private fun getLatestNews() {
        latestNewsUseCase().onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _latestNewsState.value = NewsItemListState(news = result.data)
                }
                is Resource.Error -> {
                    _latestNewsState.value =
                        NewsItemListState(error = result.message ?: "An unexpected error occured")
                }
                is Resource.Loading -> {
                    _latestNewsState.value = NewsItemListState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun getExamTypes() {
        getExamTypesUseCase.invoke().onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _examState.value = ExamState(examType = result.data ?: emptyList())
                }
                is Resource.Error -> {
                    _examState.value =
                        ExamState(error = result.message ?: "An unexpected error occurred!")
                }
                is Resource.Loading -> {
                    _examState.value = ExamState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun getFeeRecords(userUUID: String) {
        getFeesUseCase.invoke(userUUID).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _feesState.value = FeesState(records = result.data ?: emptyList())
                }
                is Resource.Error -> {
                    _feesState.value =
                        FeesState(error = result.message ?: "An unexpected error occurred!")
                }
                is Resource.Loading -> {
                    _feesState.value = FeesState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }


}