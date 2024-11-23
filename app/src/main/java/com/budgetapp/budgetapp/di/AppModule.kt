package com.budgetapp.budgetapp.di

import android.os.Build
import android.view.SurfaceControl.Transaction
import androidx.annotation.RequiresApi
import com.budgetapp.budgetapp.data.remote.TokenApi
import com.budgetapp.budgetapp.data.remote.TransactionApi
import com.budgetapp.budgetapp.util.Constant.BASE_URL
import com.budgetapp.budgetapp.util.LocalDateAdapter
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.time.LocalDate
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object AppModule {

    //used to log HTTP req and res
    @Provides
    @Singleton
    fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }

    //handles the actual network communication
    @Provides
    @Singleton
    fun provideOkHttpClient(loggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()
    }

    //simplifies api calls. user convertors to automatically serialize and deserialize json or xml data
    @RequiresApi(Build.VERSION_CODES.O)
    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        val gson: Gson = GsonBuilder()
            .registerTypeAdapter(LocalDate::class.java, LocalDateAdapter)
            .create()
        return Retrofit.Builder()
            .baseUrl(BASE_URL) // Replace with your base URL
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }

    @Provides
    @Singleton
    fun provideLinkTokenApi(retrofit: Retrofit): TokenApi {
        return retrofit.create(TokenApi::class.java)
    }

    @Provides
    @Singleton
    fun provideTransactionApi(retrofit: Retrofit): TransactionApi {
        return retrofit.create(TransactionApi::class.java)
    }


}