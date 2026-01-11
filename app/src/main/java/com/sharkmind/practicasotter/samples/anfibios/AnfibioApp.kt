package com.sharkmind.practicasotter.samples.anfibios

import android.app.Application
import coil3.ImageLoader
import coil3.SingletonImageLoader
import coil3.request.crossfade
import coil3.util.DebugLogger
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class AnfibioApp : Application() {
    override fun onCreate() {
        super.onCreate()

        SingletonImageLoader.setSafe {
            ImageLoader.Builder(this)
                .crossfade(true)
                .logger(DebugLogger())
                .build()
        }
    }
}
