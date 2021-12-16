package com.saraha.paws.Database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.saraha.paws.Model.Facts.CatFacts


@Dao
interface catFactsDao {

    @Query("SELECT * FROM catFacts")
    fun get(): List<CatFacts>

    @Insert
    fun insert(vararg facts: CatFacts)

    @Query("DELETE FROM catFacts")
    fun delete()
}