package com.example.e_progression.presentation.auth.login

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.e_progression.common.Resource
import com.example.e_progression.data.remote.dto.LoginResponse
import com.example.e_progression.domain.model.User
import com.example.e_progression.domain.use_cases.LoginUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val loginUserUseCase: LoginUserUseCase) : ViewModel() {
    private val _state = mutableStateOf(UserState())
    val state: State<UserState> = _state


    fun onBtnSignUpPress(email: String, password: String) {
        val map: HashMap<String, String> = HashMap()
        map["email"] = email
        map["password"] = password
            getUser(map)
    }

    private fun getUser(map: HashMap<String, String>) {
        loginUserUseCase.invoke(map).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _state.value = UserState(user = result.data)
                }
                is Resource.Error -> {
                    _state.value =
                        UserState(error = result.message ?: "An unexpected error occurred!")
                }
                is Resource.Loading -> {
                    _state.value = UserState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
        //return message
    }
}