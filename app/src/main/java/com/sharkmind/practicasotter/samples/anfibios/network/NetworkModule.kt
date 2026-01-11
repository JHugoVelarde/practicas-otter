package com.sharkmind.practicasotter.samples.anfibios.network

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.sharkmind.practicasotter.samples.anfibios.data.AnfibioRepository
import com.sharkmind.practicasotter.samples.anfibios.data.NetworkAnfibioRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    private const val BASE_URL = "https://android-kotlin-fun-mars-server.appspot.com/"

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
        .build()

    @Provides
    @Singleton
    fun provideAnfibioService(retrofit: Retrofit): AnfibioService =
        retrofit.create(AnfibioService::class.java)

    @Provides
    @Singleton
    fun provideAnfibioRepository(service: AnfibioService): AnfibioRepository =
        NetworkAnfibioRepository(service)
}