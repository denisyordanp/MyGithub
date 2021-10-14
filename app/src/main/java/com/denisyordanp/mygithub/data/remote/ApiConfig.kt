package com.denisyordanp.mygithub.data.remote

import com.denisyordanp.mygithub.BuildConfig
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiConfig {
    companion object {

        private const val BASE_URL = "https://api.github.com/"

        fun getApiService(): GithubService {
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build().create(GithubService::class.java)
        }

        private val loggingInterceptor =
            HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)

        private val header = Interceptor { chain ->
            val newRequest = chain.request().newBuilder()
                .addHeader("Authorization", BuildConfig.API_KEY)
                .build()

            return@Interceptor chain.proceed(newRequest)
        }

        private val client = OkHttpClient.Builder().run {
            addInterceptor(header)
            if (BuildConfig.DEBUG) addInterceptor(loggingInterceptor)
            build()
        }

    }
}