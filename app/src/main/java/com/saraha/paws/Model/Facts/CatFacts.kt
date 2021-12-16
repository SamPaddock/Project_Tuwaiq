package com.saraha.paws.Model.Facts

import androidx.room.*
import com.saraha.paws.Database.DataTypeConverter
import com.saraha.paws.Database.LinkTypeConverter

@Entity
data class CatFacts(
    @PrimaryKey val current_page: Int,
    @TypeConverters(DataTypeConverter::class) val data: List<Data>,
    val first_page_url: String,
    val from: Int,
    val last_page: Int,
    val last_page_url: String,
    @TypeConverters(LinkTypeConverter::class)val links: List<Link>,
    val next_page_url: String?,
    val path: String,
    val per_page: String,
    val prev_page_url: String?,
    val to: Int,
    val total: Int
){
}

