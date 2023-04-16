package com.digalyst.myapplication

import com.digalyst.myapplication.repo.RepositoryImpl
import android.app.Application
import com.digalyst.myapplication.data.api.ApiHelper
import com.digalyst.myapplication.data.api.ApiHelperImpl
import com.digalyst.myapplication.data.api.ApiService
import com.digalyst.myapplication.data.local.AppDatabase
import com.digalyst.myapplication.data.local.dao.UserDao
import com.digalyst.myapplication.repo.Repository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
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
    @Singleton
    fun provideRepository(
        myDao: UserDao,
        app: Application
    ): Repository = RepositoryImpl(
        application = app
    )

    @Provides
    fun provideBaseUrl() = "https://5e510330f2c0d300147c034c.mockapi.io/"

    @Singleton
    @Provides
    fun provideOkHttpClient() : OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
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
    fun provideApiService(retrofit: Retrofit) = retrofit.create(ApiService::class.java)

    @Provides
    @Singleton
    fun provideApiHelper(apiHelper: ApiHelperImpl): ApiHelper = apiHelper
}
