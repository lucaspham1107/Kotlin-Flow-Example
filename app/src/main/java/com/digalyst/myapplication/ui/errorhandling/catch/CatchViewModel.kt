package com.digalyst.myapplication.ui.errorhandling.catch

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.digalyst.myapplication.data.api.ApiHelper
import com.digalyst.myapplication.data.model.ApiUser
import com.digalyst.myapplication.repo.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CatchViewModel @Inject constructor(
    private val apiHelper: ApiHelper,
) : ViewModel() {

    private val users = MutableLiveData<Resource<List<ApiUser>>>()

    init {
        fetchUsers()
    }

    private fun fetchUsers() {
        viewModelScope.launch {
            users.postValue(Resource.Loading)
            apiHelper.getUsersWithError()
                .catch { e ->
                    users.postValue(Resource.Fail(e))
                }
                .collect {
                    users.postValue(Resource.Success(it))
                }
        }
    }

    fun getUsers(): LiveData<Resource<List<ApiUser>>> {
        return users
    }

}