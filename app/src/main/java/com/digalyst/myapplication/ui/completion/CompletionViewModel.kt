package com.mindorks.kotlinFlow.learn.completion

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.digalyst.myapplication.data.api.ApiHelper
import com.digalyst.myapplication.repo.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CompletionViewModel @Inject constructor(
    private val apiHelper: ApiHelper,
) : ViewModel() {

    private val status = MutableLiveData<Resource<String>>()

    init {
        fetchUsers()
    }

    private fun fetchUsers() {
        viewModelScope.launch {
            status.postValue(Resource.Loading)
            apiHelper.getUsers()
                .catch { e ->
                    status.postValue(Resource.Fail(e))
                }
                .onCompletion {
                    status.postValue(Resource.Success("Task Completed"))
                }
                .collect {
                }
        }
    }

    fun getStatus(): LiveData<Resource<String>> {
        return status
    }

}