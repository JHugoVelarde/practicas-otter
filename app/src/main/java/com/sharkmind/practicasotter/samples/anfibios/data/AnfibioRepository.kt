package com.sharkmind.practicasotter.samples.anfibios.data

import com.sharkmind.practicasotter.samples.anfibios.network.AnfibioData
import com.sharkmind.practicasotter.samples.anfibios.network.AnfibioService

interface AnfibioRepository {
    suspend fun getAnfibios(): List<AnfibioData>
}

class NetworkAnfibioRepository(
    private val anfibioService: AnfibioService
): AnfibioRepository {
    override suspend fun getAnfibios(): List<AnfibioData> = anfibioService.getAnfibio()
}