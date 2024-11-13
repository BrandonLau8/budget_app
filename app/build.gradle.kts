import java.io.FileInputStream
import java.io.FileNotFoundException
import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    id("com.google.dagger.hilt.android")
    alias(libs.plugins.compose.compiler)
    id("com.google.android.libraries.mapsplatform.secrets-gradle-plugin")
    id("com.google.devtools.ksp")
}



val localPropsFile = file("../local.properties")
val localProps = Properties()

if (localPropsFile.exists()) {
    localProps.load(FileInputStream(localPropsFile))
} else {
    throw FileNotFoundException("local.properties file not found")
}


android {
    namespace = "com.budgetapp.budgetapp"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.budgetapp.budgetapp"
        minSdk = 23
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }


    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            // Access clientId from local.properties
            buildConfigField("String", "CLIENT_ID", "\"${localProps["clientId"]}\"")
        }
        debug {
            // Access clientId from local.properties
            buildConfigField("String", "CLIENT_ID", "\"${localProps["clientId"]}\"")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        compose = true
        buildConfig = true // Enable BuildConfig generation
    }

    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }


}

dependencies {

    //Compose BOM
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.material3)
    implementation(libs.androidx.ui) //more custom ui

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)

    // Android Studio Preview support
    implementation(libs.androidx.compose.ui.ui.tooling.preview)
    debugImplementation(libs.ui.tooling)


    //Activity Result API
    implementation(libs.androidx.activity.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.runtime.livedata)
    implementation(libs.places)

    // UI Tests
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    implementation(libs.ui.tooling.preview)
    debugImplementation(libs.androidx.ui.tooling)

    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)

    debugImplementation(libs.androidx.ui.test.manifest)

    //Arrow
    implementation(libs.arrow.core)
    implementation(libs.arrow.fx.coroutines)

    //Retrofit
    implementation(libs.retrofit)
    implementation(libs.converter.gson)

    //Coil
    implementation(libs.coil.compose)

    //Dagger Hilt
    implementation(libs.hilt.android)
    ksp("com.google.dagger:hilt-compiler:2.51.1")
    implementation("androidx.hilt:hilt-navigation-compose:1.2.0")

    implementation("androidx.lifecycle:lifecycle-runtime-compose:2.6.2")

    //Logging Interceptor
    implementation("com.squareup.okhttp3:logging-interceptor:4.12.0")

    //Plaid
    implementation("com.plaid.link:sdk-core:4.6.0")

    //Room
    val room_version = "2.6.1"
    implementation("androidx.room:room-runtime:$room_version")
    annotationProcessor("androidx.room:room-compiler:$room_version")

    // To use Kotlin annotation processing tool (kapt)
    ksp("androidx.room:room-compiler:$room_version")

    //OAuth
//    implementation ("net.openid:appauth:0.11.1") // AppAuth for OAuth
//    implementation ("com.google.firebase:firebase-auth:21.0.1") // Firebase Auth for Google Sign-In
//    implementation ("com.google.android.gms:play-services-auth:20.0.1") // Google Play Services Auth

    implementation("androidx.credentials:credentials:1.3.0")
    implementation("androidx.credentials:credentials-play-services-auth:1.3.0")
    implementation("com.google.android.libraries.identity.googleid:googleid:1.1.1")
}

