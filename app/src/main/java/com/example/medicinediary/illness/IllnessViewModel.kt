package com.example.medicinediary.illness

import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.medicinediary.database.IllnessDatebaseDao
import com.example.medicinediary.database.IllnessMedicine
import kotlinx.coroutines.*

class IllnessViewModel( val database :IllnessDatebaseDao,application : Application) : AndroidViewModel(application)  {

    var enteredIllness : String = ""

    /////////////////////////////////

    private var viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    ///////////////////////////////////////////////////////////////////////////////////////////////////

    private val _navigateToMedicineFragment = MutableLiveData<IllnessMedicine?>()
    val navigateToMedicineFragment: LiveData<IllnessMedicine?>
        get() = _navigateToMedicineFragment

    val adapter = IllnessAdapter(IllnessClickListener {
        _navigateToMedicineFragment.value = it
    })

    fun onDoneNavigating(){
        _navigateToMedicineFragment.value = null
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }



    private var illness = MutableLiveData<IllnessMedicine>()

    val allIllnesses = database.getAllIllnesses()

    init {
        initializeIllness()
    }

    private fun initializeIllness() {
        uiScope.launch {
            illness.value = getIllnessFromDatabase()
        }
    }

    private suspend fun getIllnessFromDatabase(): IllnessMedicine? {
        return withContext(Dispatchers.IO){
            var illness = database.getIllness()
            if (illness?.medicine != "") {
                illness = null
            }
            illness
        }
    }

    fun onSubmitEntry() {
        uiScope.launch {
            val newIllness = IllnessMedicine(illness = enteredIllness)
            insert(newIllness)
            illness.value = getIllnessFromDatabase()
        }
    }


    private suspend fun insert(newIllness: IllnessMedicine) {
        withContext(Dispatchers.IO) {
            database.insert(newIllness)
        }
    }


    private suspend fun update(illness: IllnessMedicine) {
        withContext(Dispatchers.IO) {
            database.update(illness)
        }
    }

    fun deleteIllness(mIllness: IllnessMedicine) {
        uiScope.launch {
            delIllness(mIllness)
            illness.value = null
        }
    }

    suspend fun delIllness(illness: IllnessMedicine) {
        withContext(Dispatchers.IO) {
            database.deleteIllness(illness.illnessId)
        }
    }

    fun deleteMedicine(mIllness: IllnessMedicine) {
        uiScope.launch {
            delMed(mIllness)
        }
    }

    suspend fun delMed(illness: IllnessMedicine) {
        withContext(Dispatchers.IO) {
            database.deleteIllness(illness.illnessId)
        }
    }

    fun deleteEverything() {
        uiScope.launch {
            deleteAll()
        }
    }

    suspend fun deleteAll() {
        withContext(Dispatchers.IO) {
            database.deleteAll()
        }
    }


}