package com.kizenplus.weatherapp.di

import com.google.gson.Gson
import com.kizenplus.weatherapp.domain.repositries.CommonImpl
import com.kizenplus.weatherapp.domain.repositries.CommonRepository
import com.kizenplus.weatherapp.network.ApiConstants.Companion.BaseUrl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun providesUrl() = BaseUrl

    @Provides
    @Singleton
    fun apiService(url: String): ApiServices {
        return Retrofit.Builder()
            .baseUrl(url)
            .addConverterFactory(GsonConverterFactory.create())
            .client(provideOkHtttpClient(url))
            .build()
            .create(ApiServices::class.java)
    }

    private var time: Long = 1

    @Provides
    @Singleton
    fun provideOkHtttpClient(url: String): OkHttpClient {
        val okHttpClient: OkHttpClient.Builder =
            OkHttpClient.Builder()
                .addInterceptor { chain ->
                    val request =
                        chain.request().newBuilder()
                            .build()
                    chain.proceed(request)
                }

        okHttpClient.readTimeout(time, TimeUnit.MINUTES)
        okHttpClient.writeTimeout(time, TimeUnit.MINUTES)
        val client: OkHttpClient = okHttpClient.build()
        return client
    }

    @Provides
    @Singleton
    fun provideGson() = Gson()


    @Provides
    @Singleton
    fun provideCommonRepository(
        apiServices: ApiServices,
    ): CommonRepository {
        return CommonImpl(apiServices)
    }

}