package com.digalyst.myapplication.data.local

import com.digalyst.myapplication.data.local.entity.User
import kotlinx.coroutines.flow.Flow

interface DatabaseHelper {

    fun getUsers(): Flow<List<User>>

    fun insertAll(users: List<User>):Flow<Unit>

}