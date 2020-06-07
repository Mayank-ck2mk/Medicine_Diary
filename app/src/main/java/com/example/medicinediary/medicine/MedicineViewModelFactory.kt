package com.example.medicinediary.medicine

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.medicinediary.database.IllnessDatebaseDao
import com.example.medicinediary.database.IllnessMedicine

class MedicineViewModelFactory(
    private val illnessMedicine: IllnessMedicine,
    private val dataSource: IllnessDatebaseDao
) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MedicineViewModel::class.java)) {
            return MedicineViewModel(illnessMedicine,dataSource) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}