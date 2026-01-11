package com.sharkmind.practicasotter.samples.anfibios.network

import retrofit2.http.GET

interface AnfibioService {
    @GET("amphibians")
    suspend fun getAnfibio(): List<AnfibioData>
}