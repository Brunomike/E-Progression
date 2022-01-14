package com.example.e_progression.domain.use_cases

import android.util.Log
import com.example.e_progression.common.Resource
import com.example.e_progression.data.remote.dto.toFees
import com.example.e_progression.domain.model.Fees
import com.example.e_progression.domain.repository.EProgressionRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetFeesUseCase @Inject constructor(private val repository: EProgressionRepository) {
    operator fun invoke(userUUID:String): Flow<Resource<List<Fees>>> = flow {
        try {
            emit(Resource.Loading())
            //Log.d( "INVOKED: ",repository.getFeesHistory(userUUID).toString())
            val fees = repository.getFeesHistory(userUUID).map { it.toFees() }
            emit(Resource.Success(fees))
        } catch (ex: HttpException) {
            emit(Resource.Error(ex.localizedMessage ?: "An unexpected error occurred"))
        } catch (ex: IOException) {
            emit(Resource.Error("Couldn't reach server check your internet connection"))
        }
    }
}