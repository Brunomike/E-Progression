package com.example.e_progression.data.remote

import com.example.e_progression.data.remote.dto.*
import com.example.e_progression.domain.model.Exam
import com.example.e_progression.domain.model.Fees
import com.example.e_progression.domain.model.LoginData
import com.example.e_progression.domain.model.News
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface EProgressionApi {
    @POST("/api/login")
    suspend fun loginPost(@Body map:HashMap<String,String>): LoginResponse

    @POST("/api/reset-password")
    suspend fun resetPassword(@Body map: HashMap<String,String>):PassResetResponse

    @POST("/api/registration")
    suspend fun registerUser(@Body map: HashMap<String, String>):RegistrationResponse

    @GET("/api/school/news")
    suspend fun getNews():List<NewsDto>

    @GET("/api/school/news/{id}")
    suspend fun getNewsItem(@Path("id")  id:String): NewsDto

    @GET("api/school/news/latest/one")
    suspend fun getLatestNews():NewsDto

    @GET("/api/user/profile/{userUUID}")
    suspend fun getProfileDetails(@Path("userUUID") userUUID:String):UserDto

    @GET("/api/get/all-exam-type")
    suspend fun getExamTypes():List<ExamType>

    @GET("/api/student/results/{userUUID}")
    suspend fun getExamResults(@Path("userUUID") userUUID:String):List<ResultDto>

    @GET("/api/student/fees/{userUUID}")
    suspend fun getFeeRecords(@Path("userUUID") userUUID: String):List<FeesDto>

    @GET("/api/school/news/{id}")
    suspend fun getFeeItem(@Path("id")  id:String): FeesDto

    @GET("api/student/fees/{id}")
    suspend fun viewFeeRecord(@Path("id") id:String):FeesDto

    @GET("api/fee/summary")
    suspend fun getFeeSummary()

    @GET("/api/student/no/{userUUID}")
    suspend fun getStudentNo(@Path("userUUID") userUUID:String):List<StudentDto>

}