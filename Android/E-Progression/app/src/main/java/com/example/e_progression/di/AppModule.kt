package com.example.e_progression.di

import android.util.Log
import com.example.e_progression.common.Constants
import com.example.e_progression.data.remote.EProgressionApi
import com.example.e_progression.data.repository.EProgressionRepositoryImplementation
import com.example.e_progression.domain.repository.EProgressionRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun providesEProgressionApi(): EProgressionApi {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(EProgressionApi::class.java)
    }

    @Provides
    @Singleton
    fun provideEProgressionRepository(api: EProgressionApi): EProgressionRepository {
        //Log.d("APPMODULE", "called")
        return EProgressionRepositoryImplementation(api)
    }

}
