package com.android.example.library.ui.activities.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.android.example.library.util.Resource

open class BaseViewModel : ViewModel() {
    var _status = MutableLiveData<Resource<String>>()
    val status: LiveData<Resource<String>> = _status
}