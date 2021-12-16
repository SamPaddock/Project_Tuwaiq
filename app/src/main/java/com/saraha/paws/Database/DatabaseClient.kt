package com.saraha.paws.Database

import android.content.Context
import androidx.room.Room

class DatabaseClient {

    var context: Context? = null
    var dbInstance: DatabaseClient? = null
    private var appDatabase: AppDatabase? = null

    constructor(context: Context?){
        this.context = context
        appDatabase =
            context?.let { Room.databaseBuilder(it, AppDatabase::class.java, "paws-database").allowMainThreadQueries().build() }
    }

    @Synchronized
    fun getInstance(context: Context?): DatabaseClient? {
        if (dbInstance == null) {
            dbInstance = DatabaseClient(context)
        }
        return dbInstance
    }

    fun getAppDatabase(): AppDatabase? {
        return appDatabase
    }

}