package com.example.e_progression.presentation.main.profile

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.e_progression.common.Constants
import com.example.e_progression.common.Resource
import com.example.e_progression.domain.use_cases.GetProfileUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val getProfileUseCase: GetProfileUseCase,
    savedStateHandle: SavedStateHandle
) :
    ViewModel() {
    private val _state = mutableStateOf(ProfileState())
    val state: State<ProfileState> = _state

    init {
        getProfile(Constants.PARAM_USER_UUID)
    }

    private fun getProfile(userUUID:String) {
        getProfileUseCase.invoke(userUUID).onEach { result->
            when (result) {
                is Resource.Success -> {
                    _state.value = ProfileState(profile = result.data)
                }
                is Resource.Error -> {
                    _state.value =
                        ProfileState(error = result.message ?: "An unexpected error occurred!")
                }
                is Resource.Loading -> {
                    _state.value = ProfileState(isLoading = true)
                }
            }

        }.launchIn(viewModelScope)

    }
}