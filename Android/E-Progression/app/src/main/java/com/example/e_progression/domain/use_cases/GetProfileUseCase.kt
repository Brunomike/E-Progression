package com.example.e_progression.domain.use_cases

import android.util.Log
import com.example.e_progression.common.Resource
import com.example.e_progression.data.remote.dto.UserDto
import com.example.e_progression.data.remote.dto.toNews
import com.example.e_progression.data.remote.dto.toUser
import com.example.e_progression.domain.model.News
import com.example.e_progression.domain.model.User
import com.example.e_progression.domain.repository.EProgressionRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetProfileUseCase @Inject constructor(private val repository: EProgressionRepository) {
    operator fun invoke(userUUID:String): Flow<Resource<User>> = flow {
        try {
            emit(Resource.Loading())
            val profile: UserDto = repository.getProfileDetails(userUUID)
            emit(Resource.Success(profile.toUser()))
        } catch (ex: HttpException) {
            emit(Resource.Error(ex.localizedMessage ?: "An unexpected error occurred"))
        } catch (ex: IOException) {
            emit(Resource.Error("Couldn't reach server check your internet connection"))
        }
    }
}