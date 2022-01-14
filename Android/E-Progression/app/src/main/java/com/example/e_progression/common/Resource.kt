package com.example.e_progression.common

sealed class Resource<T>(val data:T?=null,val message:String?=null){
    class Success<T>(data:T):Resource<T>(data)
    class Error<T>(message: String,data:T?=null):Resource<T>(data,message)
    class Loading<T>(data: T?=null):Resource<T>(data)
    var user_UUID="ada7c934-e68b-478e-adbe-11887a9d0b70"
}
