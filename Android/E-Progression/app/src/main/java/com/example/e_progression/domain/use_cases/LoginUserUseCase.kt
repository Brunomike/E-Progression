package com.example.e_progression.domain.use_cases

import android.util.Log
import com.example.e_progression.common.Resource
import com.example.e_progression.data.remote.dto.LoginResponse
import com.example.e_progression.domain.repository.EProgressionRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject


class LoginUserUseCase @Inject constructor(
    private val repository: EProgressionRepository
) {
    operator fun invoke(map:HashMap<String,String>): Flow<Resource<LoginResponse>> = flow{
        try {
            emit(Resource.Loading())
            val response:LoginResponse=repository.loginUser(map)
            Log.d( "LOGIN REQUEST: ",response.toString())
            emit(Resource.Success(response))
        } catch (ex: HttpException) {
            emit(Resource.Error(ex.localizedMessage ?: "An unexpected error occurred"))
        } catch (ex: IOException) {
            emit(Resource.Error("Couldn't reach server check your internet connection"))
        }
    }
}