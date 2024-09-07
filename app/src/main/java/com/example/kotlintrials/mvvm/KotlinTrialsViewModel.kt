package com.example.kotlintrials.mvvm

import androidx.lifecycle.MutableLiveData

class KotlinTrialsViewModel {
    val name = MutableLiveData<String>()
    val imageUrl = MutableLiveData<String>()
    val message = MutableLiveData<String>()
}