package com.digalyst.myapplication.repo

import android.app.Application
import com.digalyst.myapplication.repo.Repository

class RepositoryImpl(
    private val application: Application
) : Repository {

    companion object {
        const val TAG = "RepositoryImpl"
    }


}