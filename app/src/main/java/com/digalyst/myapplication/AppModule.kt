package com.digalyst.myapplication

import com.digalyst.myapplication.repo.RepositoryImpl
import android.app.Application
import com.digalyst.myapplication.data.api.ApiHelper
import com.digalyst.myapplication.data.api.ApiHelperImpl
import com.digalyst.myapplication.data.api.ApiService
import com.digalyst.myapplication.data.local.AppDatabase
import com.digalyst.myapplication.data.local.dao.UserDao
import com.ihsanbal.logging.Level
import com.ihsanbal.logging.LoggingInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.internal.platform.Platform
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideAppDatabase(app: Application) = AppDatabase.getInstance(app)

    @Provides
    fun provideDataBaseDao(appDatabase: AppDatabase): UserDao {
        return appDatabase.myDao()
    }

    @Provides
    fun provideBaseUrl() = "https://5e510330f2c0d300147c034c.mockapi.io/"

    @Singleton
    @Provides
    fun provideOkHttpClient(loggingInterceptor: LoggingInterceptor) : OkHttpClient {
        val httpInterceptor = HttpLoggingInterceptor()
        httpInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .addInterceptor(httpInterceptor)
            .build()
    }


    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient, BASE_URL:String): Retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(BASE_URL)
        .client(okHttpClient)
        .build()

    @Provides
    @Singleton
    fun provideLoggingInterceptorLevelBody(): LoggingInterceptor {
        return LoggingInterceptor.Builder()
            .setLevel(Level.BASIC)
            .log(Platform.INFO)
            .request(String.format("%s-Request", "FLOW-DEMO"))
            .response(String.format("%s-Response", "FLOW-DEMO"))
            .build()
    }

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit) = retrofit.create(ApiService::class.java)

    @Provides
    @Singleton
    fun provideApiHelper(apiHelper: ApiHelperImpl): ApiHelper = apiHelper
}
