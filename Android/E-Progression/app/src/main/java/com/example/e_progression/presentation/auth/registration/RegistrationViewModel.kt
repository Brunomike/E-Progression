package com.example.e_progression.presentation.auth.registration

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.e_progression.common.Resource
import com.example.e_progression.domain.use_cases.RegistrationUseCase
import com.example.e_progression.presentation.auth.forgot.ResetPasswordState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class RegistrationViewModel @Inject constructor(private val registrationUseCase: RegistrationUseCase):ViewModel(){
    private val _state = mutableStateOf(RegistrationState())
    val state: State<RegistrationState> = _state

    fun onBtnSignUpClick(firstName:String,lastName:String,emailAddress: String,gender:String,phone:String,dob:String, password: String,confirmPassword:String) {
        val map: HashMap<String, String> = HashMap()
        map["firstName"]=firstName
        map["lastName"]=lastName
        map["emailAddress"] = emailAddress
        map["gender"]=gender
        map["phone"]=phone
        map["dob"]=dob
        map["password"] = password
        map["confirmPassword"]=confirmPassword
        registerUser(map)
    }

    private fun registerUser(map:HashMap<String,String>){
        registrationUseCase.invoke(map).onEach { result->
            when (result) {
                is Resource.Success -> {
                    _state.value = RegistrationState(message = result.data)
                }
                is Resource.Error -> {
                    _state.value =
                        RegistrationState(error = result.message ?: "An unexpected error occurred!")
                }
                is Resource.Loading -> {
                    _state.value = RegistrationState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }
}