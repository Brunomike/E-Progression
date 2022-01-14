package com.example.e_progression.data.repository

import com.example.e_progression.data.remote.EProgressionApi
import com.example.e_progression.data.remote.dto.*
import com.example.e_progression.domain.repository.EProgressionRepository
import javax.inject.Inject

class EProgressionRepositoryImplementation @Inject constructor(private val api: EProgressionApi):EProgressionRepository {
    override suspend fun loginUser(map:HashMap<String,String>): LoginResponse {
        return api.loginPost(map)
    }

    override suspend fun resetPassword(map: HashMap<String, String>): PassResetResponse {
        return api.resetPassword(map)
    }

    override suspend fun registerUser(map: HashMap<String, String>): RegistrationResponse {
        return api.registerUser(map)
    }
    override suspend fun getProfileDetails(userUUID:String): UserDto {
        return api.getProfileDetails(userUUID)
    }

    override suspend fun getExamTypes(): List<ExamType> {
        return api.getExamTypes()
    }

    override suspend fun getExamResults(userUUID:String): List<ResultDto> {
        return api.getExamResults(userUUID)
    }

    override suspend fun getNews(): List<NewsDto> {
        return api.getNews()
    }

    override suspend fun getNewsItem(id: String): NewsDto {
        return api.getNewsItem(id)
    }

    override suspend fun getLatestNews(): NewsDto {
        return api.getLatestNews()
    }

    override suspend fun getFeesHistory(userUUID: String): List<FeesDto> {
        return  api.getFeeRecords(userUUID)
    }

    override suspend fun getFeeItem(id: String): FeesDto {
        return api.getFeeItem(id)
    }

    override suspend fun getStudentNo(userUUID: String): List<StudentDto> {
        return api.getStudentNo(userUUID)
    }

}