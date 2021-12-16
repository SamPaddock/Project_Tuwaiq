package com.saraha.paws.Database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.saraha.paws.Model.Facts.CatFacts


@Database(entities = [CatFacts::class], version = 1)
@TypeConverters(DataTypeConverter::class, LinkTypeConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun catFactsDao(): catFactsDao
}