package com.example.e_progression.domain.use_cases

import android.util.Log
import com.example.e_progression.common.Resource
import com.example.e_progression.data.remote.dto.ExamType
import com.example.e_progression.data.remote.dto.toExam
import com.example.e_progression.data.remote.dto.toNews
import com.example.e_progression.domain.model.Exam
import com.example.e_progression.domain.model.News
import com.example.e_progression.domain.repository.EProgressionRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetExamTypesUseCase @Inject constructor(private val repository: EProgressionRepository) {
    operator fun invoke(): Flow<Resource<List<Exam>>> = flow {
        try {
            emit(Resource.Loading())
            val exams = repository.getExamTypes().map { it.toExam() }
            emit(Resource.Success(exams))
        } catch (ex: HttpException) {
            emit(Resource.Error(ex.localizedMessage ?: "An unexpected error occurred"))
        } catch (ex: IOException) {
            Log.d("invoke: ", ex.message.toString())
            emit(Resource.Error("Couldn't reach server check your internet connection"))
        }
    }

}