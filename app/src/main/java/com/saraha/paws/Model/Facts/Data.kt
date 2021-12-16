package com.saraha.paws.Model.Facts

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity
data class Data(
    val fact: String,
    val length: Int
):Serializable {
    override fun toString(): String {
        return super.toString()
    }
}