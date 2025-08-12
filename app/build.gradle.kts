plugins {

//    alias(libs.plugins.android.application) apply false
//    alias(libs.plugins.compose.compiler) apply false
//    alias(libs.plugins.jetbrains.kotlin.android) apply false
//    alias(libs.plugins.dagger.hilt) apply false
//    alias(libs.plugins.ksp) apply false
//    alias(libs.plugins.kotlin.serialization) apply false


    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    id("com.google.devtools.ksp")


//    for hilt ksp
    id("kotlin-kapt")

    id("com.google.dagger.hilt.android")
//    for serialization
    kotlin("plugin.serialization") version "2.1.20"


}


android {
    namespace = "com.example.imageapp"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.imageapp"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"),
                          "proguard-rules.pro")
            signingConfig = signingConfigs.getByName("debug")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.animation.core.android)
    implementation(libs.androidx.animation.core.android)
    implementation(libs.androidx.animation.core.android)
    implementation(libs.androidx.animation.core.android)
    implementation(libs.androidx.palette.ktx)
    implementation(libs.volley)
    implementation(libs.accessibility.test.framework)
    implementation(libs.accessibility.test.framework)
    implementation(libs.accessibility.test.framework)
    implementation(libs.androidx.runner)
    implementation(libs.androidx.runner)
    implementation(libs.androidx.espresso.core)
    implementation(libs.androidx.foundation.layout.android)
    implementation(libs.androidx.foundation.layout.android)
    implementation(libs.androidx.paging.compose.android)
    implementation(libs.identity.credential)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

//    implementation ("com.google.accompanist:accompanist-webview:0.30.1")
    // Jetpack Compose integration
    implementation(libs.androidx.navigation.compose)

    // Room
    ksp(libs.androidx.room.compiler)
    ksp(libs.androidx.room.compiler.v250)
    implementation(libs.androidx.room.runtime)
    implementation(libs.androidx.room.ktx)
    implementation(libs.androidx.room.paging)


// //Splash Screen
//    implementation(libs.androidx.core.splashscreen)

    implementation(libs.androidx.core.splashscreen)
//
//    //Cloudy for blurring effect
//    implementation(libs.cloudy)
    implementation(libs.cloudy)
//
//    //Paging
//    implementation(libs.androidx.paging.runtime.ktx)
//    implementation(libs.androidx.paging.compose)
//

    val paging_version = "3.3.6"
    implementation("androidx.paging:paging-runtime:$paging_version")
    implementation("androidx.paging:paging-compose:3.3.6")

//
    // Retrofit


    // retrofit
//    by website
//    implementation(libs.squareup.retrofit)
//    // GSON
//    implementation(libs.converter.gson)
//    // coroutine
//    implementation(libs.kotlinx.coroutines.android)
//    implementation(libs.kotlinx.coroutines.core)

    implementation(libs.squareup.retrofit)
    implementation(libs.converter.gson)
    implementation(libs.converter.scalars)
    implementation(libs.retrofit2.kotlinx.serialization.converter)
    implementation(libs.okhttp)
    implementation(libs.kotlinx.serialization.json)


//
//    //Dagger-Hilt
    implementation(libs.hilt.android)
    kapt(libs.hilt.android.compiler)
    ksp(libs.androidx.hilt.compiler)
    implementation(libs.androidx.hilt.navigation.compose)
//    implementation(libs.hilt.android)
//    ksp(libs.hilt.android.compiler)

//
//    ksp(libs.androidx.hilt.compiler)
//    implementation(libs.androidx.hilt.navigation.compose)


    implementation(libs.coil.compose)
    implementation(libs.coil.network.okhttp)


    implementation(libs.androidx.webkit)
//    Simring effect
    implementation (libs.accompanist.placeholder.material)

    implementation("com.google.accompanist:accompanist-pager:0.34.0") // check latest
    implementation("com.google.accompanist:accompanist-pager-indicators:0.34.0")

    implementation("io.coil-kt:coil-compose:2.4.0")



}