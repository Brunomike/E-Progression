package com.example.e_progression.presentation.main.results

import android.content.Context
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.e_progression.common.Constants
import com.example.e_progression.common.Resource
import com.example.e_progression.domain.use_cases.GetExamResultsUseCase
import com.example.e_progression.domain.use_cases.GetExamTypesUseCase
import com.example.e_progression.domain.use_cases.GetStudentNoUseCase
import com.example.e_progression.presentation.main.fees.StudentsState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class ResultsViewModel @Inject constructor(private val getExamTypesUseCase: GetExamTypesUseCase,private val getExamResultsUseCase: GetExamResultsUseCase,private val getStudentNoUseCase: GetStudentNoUseCase) :
    ViewModel() {
    private val _state = mutableStateOf(ExamState())
    val state: State<ExamState> = _state

    private val _resultState= mutableStateOf(ResultState())
    val resultState:State<ResultState> = _resultState

    private val _studentsState= mutableStateOf(StudentsState())
    val studentsState:State<StudentsState> = _studentsState




    init {
        getExamTypes()
        getExamResults(Constants.PARAM_USER_UUID)
        getStudentsCount(Constants.PARAM_USER_UUID)

    }

    private fun getStudentsCount(userUUID: String){
        getStudentNoUseCase.invoke(userUUID).onEach { result->
            when (result) {
                is Resource.Success -> {
                    _studentsState.value = StudentsState(students = result.data ?: emptyList())
                }
                is Resource.Error -> {
                    _studentsState.value =
                        StudentsState(error = result.message ?: "An unexpected error occurred!")
                }
                is Resource.Loading -> {
                    _studentsState.value = StudentsState(isLoading = true)
                }
            }
        } .launchIn(viewModelScope)
    }


    private fun getExamTypes() {
        getExamTypesUseCase.invoke().onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _state.value = ExamState(examType = result.data ?: emptyList())
                }
                is Resource.Error -> {
                    _state.value =
                        ExamState(error = result.message ?: "An unexpected error occurred!")
                }
                is Resource.Loading -> {
                    _state.value = ExamState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun getExamResults(userUUID:String){
        getExamResultsUseCase.invoke(userUUID).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _resultState.value = ResultState(results = result.data ?: emptyList())
                }
                is Resource.Error -> {
                    _resultState.value =
                        ResultState(error = result.message ?: "An unexpected error occurred!")
                }
                is Resource.Loading -> {
                    _resultState.value = ResultState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }


    private fun getStudentNo(){

    }
}
