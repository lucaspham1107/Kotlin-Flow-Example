package com.mindorks.kotlinFlow.learn.retrofit.series

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.digalyst.myapplication.data.api.ApiHelper
import com.digalyst.myapplication.data.model.ApiUser
import com.digalyst.myapplication.repo.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SeriesNetworkCallsViewModel @Inject constructor(
    private val apiHelper: ApiHelper,
) : ViewModel() {

    private val users = MutableLiveData<Resource<List<ApiUser>>>()

    init {
        fetchUsers()
    }

    @OptIn(FlowPreview::class)
    private fun fetchUsers() {
        viewModelScope.launch {
            users.postValue(Resource.Loading)
            val allUsersFromApi = mutableListOf<ApiUser>()
            apiHelper.getUsers()
                .flatMapConcat { usersFromApi ->
                    allUsersFromApi.addAll(usersFromApi)
                    apiHelper.getMoreUsers()
                }
                .flowOn(Dispatchers.Default)
                .catch { e ->
                    users.postValue(Resource.Fail(e))
                }
                .collect { moreUsersFromApi ->
                    allUsersFromApi.addAll(moreUsersFromApi)
                    users.postValue(Resource.Success(allUsersFromApi))
                }
        }
    }

    fun getUsers(): LiveData<Resource<List<ApiUser>>> {
        return users
    }

}