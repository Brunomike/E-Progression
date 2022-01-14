package com.example.e_progression.domain.use_cases

import android.util.Log
import com.example.e_progression.common.Resource
import com.example.e_progression.data.remote.dto.toExam
import com.example.e_progression.data.remote.dto.toResults
import com.example.e_progression.domain.model.Exam
import com.example.e_progression.domain.model.Results
import com.example.e_progression.domain.repository.EProgressionRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetExamResultsUseCase @Inject constructor(private val repository: EProgressionRepository) {
    operator fun invoke(userUUID:String): Flow<Resource<List<Results>>> = flow {
        try {
            emit(Resource.Loading())
            val exams = repository.getExamResults(userUUID = userUUID).map { it.toResults() }
            Log.d( "EXAMS: ",exams.toString())
            emit(Resource.Success(exams))
        } catch (ex: HttpException) {
            emit(Resource.Error(ex.localizedMessage ?: "An unexpected error occurred"))
        } catch (ex: IOException) {
            Log.d("invoke: ", ex.message.toString())
            emit(Resource.Error("Couldn't reach server check your internet connection"))
        }
    }

}