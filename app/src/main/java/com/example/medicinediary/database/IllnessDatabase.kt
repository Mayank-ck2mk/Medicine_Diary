package com.example.medicinediary.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(entities = [IllnessMedicine :: class], version = 1, exportSchema = false)
abstract class IllnessDatabase : RoomDatabase() {

    abstract val illnessDatabaseDao : IllnessDatebaseDao

    companion object{

        @Volatile
        private var INSTANCE : IllnessDatabase? = null // INSTANCE will keep a reference to the database once we have one. This will help us avoid repeatedly opening connections to the database which is expensive. @Volatile means -> Helps us make sure that the value of the INSTANCE is always up to date

        fun getInstance(context: Context) : IllnessDatabase {
            synchronized(this){
                var instance = INSTANCE

                if(instance == null){                   // checking if there's already a database
                    instance = Room.databaseBuilder(
                        context.applicationContext,IllnessDatabase::class.java,
                        "illness_record_database"
                    )
                        .fallbackToDestructiveMigration()
                        .build()

                    INSTANCE = instance

                }

                return instance
            }

        }

    }

}