package com.digalyst.myapplication.ui.filter

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.digalyst.myapplication.data.api.ApiHelper
import com.digalyst.myapplication.repo.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FilterViewModel @Inject constructor(
) : ViewModel() {
    private val status = MutableLiveData<Resource<String>>()

    fun startFilterTask() {
        viewModelScope.launch {
            status.postValue(Resource.Loading)
            val result = mutableListOf<Int>()
            (1..5).asFlow()
                .filter {
                    it % 2 == 0
                }
                .toList(result)

            status.postValue(Resource.Success(result.toString()))
        }
    }

    fun getStatus(): LiveData<Resource<String>> {
        return status
    }

}