package com.example.medicinediary.database

import android.os.FileObserver.DELETE
import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface IllnessDatebaseDao {

    @Insert
    fun insert(completeIllness: IllnessMedicine)

    @Update
    fun update(completeIllness: IllnessMedicine)

    @Delete
    fun delete(completeIllness: IllnessMedicine)

    @Query("DELETE from illness_medicine_table")
    fun deleteAll()

    @Query("SELECT * from illness_medicine_table WHERE illness_id = :key")
    fun get(key: Long): IllnessMedicine

    @Query("DELETE  FROM illness_medicine_table WHERE illness_id = :key")
    fun deleteIllness(key: Long) : Int

    @Query("UPDATE illness_medicine_table SET medicine_name ='',expiry_date='',photo_id = -1, prescription_id = -1 WHERE illness_id = :key ")
    fun deleteMedicine(key: Long) : Int

    @Query("SELECT * FROM illness_medicine_table ORDER BY illness_id DESC LIMIT 1")
    fun getIllness() : IllnessMedicine?

    @Query("SELECT * FROM illness_medicine_table ORDER BY illness_id ASC")
    fun getAllIllnesses() : LiveData<List<IllnessMedicine>>
}