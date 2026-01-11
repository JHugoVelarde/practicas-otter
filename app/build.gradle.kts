plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    // Hilt
    alias(libs.plugins.dagger.hilt.android)
    // Ksp - para Hilt y Room
    alias(libs.plugins.com.google.devtools.ksp)
    // Kotlin Serialization
    alias(libs.plugins.kotlin.serialization)
    // id("org.jetbrains.kotlin.plugin.serialization") version "2.2.10"
}

android {
    namespace = "com.sharkmind.practicasotter"
    compileSdk {
        version = release(36)
    }

    defaultConfig {
        applicationId = "com.sharkmind.practicasotter"
        minSdk = 26
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlin {
        jvmToolchain(17)
    }
    buildFeatures {
        compose = true
    }
    ksp {
        arg("room.schemaLocation", "$projectDir/schemas")
    }
}

dependencies {
    // Kotlin Serialization
    implementation(libs.kotlinx.serialization.json)
    // Gson
    implementation(libs.google.gson)
    // Accompanist (para rememberPermissionState)
    implementation(libs.accompanist.permissions)
    // Icons extended
    implementation(libs.material.icons.extended)
    // Coil
    implementation(libs.coil.core)
    implementation(libs.coil.compose)
    implementation(libs.coil.network.okhttp)
    // ViewModel
    implementation(libs.lifecycle.viewmodel.compose)
    implementation(libs.androidx.lifecycle.runtime.compose)
    // Retrofit
    implementation(libs.com.squareup.retrofit2.retrofit)
    // Retrofit with Scalar Converter
    // implementation("com.squareup.retrofit2:converter-scalars:2.9.0")
    // Retrofit with Kotlin serialization Converter
    // implementation("com.jakewharton.retrofit:retrofit2-kotlinx-serialization-converter:1.0.0")
    // implementation("com.squareup.okhttp3:okhttp:4.11.0")
    implementation(libs.com.squareup.retrofit2.converter.scalars)
    implementation(libs.kotlinx.serialization.converter)
    implementation(libs.com.squareup.okhttp)
    // Services
    implementation(libs.androidx.lifecycle.service)
    implementation(libs.androidx.compose.runtime.livedata)
    implementation(libs.androidx.lifecycle.process)
    // Hilt
    implementation(libs.dagger.hilt.android)
    implementation(libs.hilt.navigation.compose)
    implementation(libs.javax.inject)
    implementation(libs.androidx.ui)
    ksp(libs.dagger.hilt.compiler)
    // Room
    implementation(libs.androidx.room.runtime)
    ksp(libs.androidx.room.compiler) {
        exclude(group = "com.squareup", module = "javapoet")
    }
    implementation(libs.androidx.room.ktx)
    // Navigation
    implementation(libs.navigation.compose)
    // DataStore
    implementation(libs.androidx.datastore.preferences)

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.compose.material3)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
    debugImplementation(libs.androidx.compose.ui.tooling)
    debugImplementation(libs.androidx.compose.ui.test.manifest)
}