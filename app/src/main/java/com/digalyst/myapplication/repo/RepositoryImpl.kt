package com.digalyst.myapplication.repo

import android.app.Application
import com.digalyst.myapplication.data.api.ApiHelper
import javax.inject.Inject

class RepositoryImpl @Inject constructor(
    private val application: Application,
)  {

    companion object {
        const val TAG = "RepositoryImpl"
    }



}