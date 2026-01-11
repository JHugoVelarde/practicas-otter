package com.sharkmind.practicasotter.samples.anfibios.network

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AnfibioData(
    val name: String,
    val type: String,
    val description: String,
    @SerialName("img_src") val imgSrc: String
)
