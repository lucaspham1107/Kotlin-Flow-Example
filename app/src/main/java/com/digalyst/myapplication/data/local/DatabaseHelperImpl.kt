package com.digalyst.myapplication.data.local

import com.digalyst.myapplication.data.local.AppDatabase
import com.digalyst.myapplication.data.local.DatabaseHelper
import com.digalyst.myapplication.data.local.entity.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class DatabaseHelperImpl(private val appDatabase: AppDatabase) : DatabaseHelper {

    override fun getUsers(): Flow<List<User>> =
        flow { emit(appDatabase.myDao().getAll()) }

    override fun insertAll(users: List<User>): Flow<Unit> = flow {
        appDatabase.myDao().insertAll(users)
        emit(Unit)
    }

}