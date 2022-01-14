package com.example.e_progression.domain.repository

import com.example.e_progression.data.remote.dto.*

interface EProgressionRepository {
    suspend fun loginUser(map:HashMap<String,String>):LoginResponse
    suspend fun resetPassword(map:HashMap<String,String>):PassResetResponse
    suspend fun registerUser(map: HashMap<String, String>):RegistrationResponse
    suspend fun getProfileDetails(userUUID:String):UserDto
    suspend fun getExamTypes():List<ExamType>
    suspend fun getExamResults(userUUID:String):List<ResultDto>
    suspend fun getNews(): List<NewsDto>
    suspend fun getNewsItem(id:String):NewsDto
    suspend fun getLatestNews():NewsDto
    suspend fun getFeesHistory(userUUID:String):List<FeesDto>
    suspend fun getFeeItem(id:String):FeesDto
    suspend fun getStudentNo(userUUID: String):List<StudentDto>
}