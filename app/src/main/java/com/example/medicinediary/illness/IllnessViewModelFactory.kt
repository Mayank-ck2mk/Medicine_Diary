package com.example.medicinediary.illness

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.medicinediary.database.IllnessDatebaseDao

class IllnessViewModelFactory(
    private val dataSource: IllnessDatebaseDao,
    private val application: Application
) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(IllnessViewModel::class.java)) {
            return IllnessViewModel(dataSource, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}