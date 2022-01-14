package com.example.e_progression.presentation.main.fees

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.e_progression.common.Constants
import com.example.e_progression.common.Resource
import com.example.e_progression.domain.use_cases.GetFeesUseCase
import com.example.e_progression.domain.use_cases.GetStudentNoUseCase
import com.example.e_progression.presentation.main.results.ResultState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject


@HiltViewModel
class FeesViewModel @Inject constructor(private val getFeesUseCase: GetFeesUseCase,private val getStudentNoUseCase: GetStudentNoUseCase):ViewModel(){
    private val _state = mutableStateOf(FeesState())
    val state: State<FeesState> = _state

    private val _studentsState= mutableStateOf(StudentsState())
    val studentsState:State<StudentsState> = _studentsState

    init {
        getFeeRecords(Constants.PARAM_USER_UUID)
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

    private fun getFeeRecords(userUUID:String){
        getFeesUseCase.invoke(userUUID).onEach { result->
            when (result) {
                is Resource.Success -> {
                    _state.value = FeesState(records = result.data ?: emptyList())
                }
                is Resource.Error -> {
                    _state.value =
                        FeesState(error = result.message ?: "An unexpected error occurred!")
                }
                is Resource.Loading -> {
                    _state.value = FeesState(isLoading = true)
                }
            }
        }  .launchIn(viewModelScope)
    }
}