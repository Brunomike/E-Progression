package com.example.e_progression.presentation.auth.forgot

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.e_progression.common.Resource
import com.example.e_progression.domain.use_cases.PasswordResetUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class ResetPasswordViewModel @Inject constructor(private val passwordResetUseCase: PasswordResetUseCase):ViewModel(){
    private val _state = mutableStateOf(ResetPasswordState())
    val state: State<ResetPasswordState> = _state

    fun onBtnSubmitPress(email: String, password: String,confirmPassword:String) {
        val map: HashMap<String, String> = HashMap()
        map["email"] = email
        map["password"] = password
        map["confirmPassword"]=confirmPassword
        resetUserPassword(map)
    }

    private fun resetUserPassword(map:HashMap<String,String>){
        passwordResetUseCase.invoke(map).onEach { result->
            when (result) {
                is Resource.Success -> {
                    _state.value = ResetPasswordState(message = result.data)
                }
                is Resource.Error -> {
                    _state.value =
                        ResetPasswordState(error = result.message ?: "An unexpected error occurred!")
                }
                is Resource.Loading -> {
                    _state.value = ResetPasswordState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }

}