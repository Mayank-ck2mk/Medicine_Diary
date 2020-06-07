package com.example.medicinediary.medicine

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.medicinediary.database.IllnessDatebaseDao
import com.example.medicinediary.database.IllnessMedicine
import kotlinx.coroutines.*

class MedicineViewModel (val illnessMedicine: IllnessMedicine, val database: IllnessDatebaseDao): ViewModel() {

    private var medicineViewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + medicineViewModelJob)

    var medicineName : String = ""
    var expiryDate : String = ""

    private val _openMedicineDialogBox = MutableLiveData<Boolean>()
    val openMedicineDialogBox : LiveData<Boolean>
    get() = _openMedicineDialogBox

    private val _medicineNameEntered = MutableLiveData<Boolean>()
    val medicineNameEntered : LiveData<Boolean>
        get() = _medicineNameEntered



    fun onFabClicked(){
        _openMedicineDialogBox.value = true
    }

    fun onDialogBoxOpened(){
        _openMedicineDialogBox.value = false
    }

    fun onSubmitEntry(){
        illnessMedicine.medicine = medicineName
        illnessMedicine.expiryDate = expiryDate
        uiScope.launch {
            update(illnessMedicine)
        }
    }

    private suspend fun update(illness: IllnessMedicine) {
        withContext(Dispatchers.IO) {
            database.update(illness)
        }
    }


    fun onMedicineNameEntered(){
        _medicineNameEntered.value = true
    }



//    private val _navigateToSearch = MutableLiveData<Boolean>()
//    val navigateToSearch: LiveData<Boolean>
//        get() = _navigateToSearch
//
//    fun onFabClicked() {
//        _navigateToSearch.value = true
//    }
//
//    fun onNavigatedToSearch() {
//        _navigateToSearch.value = false
//    }


}