package com.digalyst.myapplication.ui.map

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.digalyst.myapplication.data.api.ApiHelper
import com.digalyst.myapplication.data.local.entity.User
import com.digalyst.myapplication.repo.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MapViewModel @Inject constructor(
    val apiHelper: ApiHelper
) : ViewModel() {

    private val users = MutableLiveData<Resource<List<User>>>()

    init {
        fetchUsers()
    }

    private fun fetchUsers() {
        viewModelScope.launch {
            users.postValue(Resource.Loading)
            apiHelper.getUsers()
                .map { apiUserList ->
                    val userList = mutableListOf<User>()
                    for (apiUser in apiUserList) {
                        val user = User(
                            apiUser.id,
                            apiUser.name + "123" ,
                            apiUser.email,
                            apiUser.avatar
                        )
                        userList.add(user)
                    }
                    userList
                }
                .catch { e ->
                    users.postValue(Resource.Fail(e))
                }
                .collect {
                    users.postValue(Resource.Success(it))
                }
        }
    }

    fun getUsers(): LiveData<Resource<List<User>>> {
        return users
    }
}