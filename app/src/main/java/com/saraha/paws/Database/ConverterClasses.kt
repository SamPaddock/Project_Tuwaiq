package com.saraha.paws.Database

import androidx.room.TypeConverter
import com.google.common.reflect.TypeToken
import com.google.gson.Gson
import com.saraha.paws.Model.Facts.CatFacts
import com.saraha.paws.Model.Facts.Data
import com.saraha.paws.Model.Facts.Link
import org.json.JSONArray
import org.json.JSONObject

class DataTypeConverter {
    @TypeConverter
    fun fromData(source: List<Data>): String {

        val jsonArray = JSONArray()
        source.forEach {
            val jasonObj = JSONObject().apply {
                put("fact", it.fact)
                put("length", it.length)
            }
            jsonArray.put(jasonObj)
        }

        return jsonArray.toString()
    }

    @TypeConverter
    fun toData(source: String): List<Data> {
        val typeToken = object : TypeToken<List<Data>>() {}.type
        var jsonArray = Gson().fromJson<List<Data>>(source,typeToken)
        return jsonArray
    }
}

class LinkTypeConverter {
    @TypeConverter
    fun fromLink(source: List<Link>): String {
        val jsonArray = JSONArray()
        source.forEach {
            val jasonObj = JSONObject().apply {
                put("active", it.active)
                put("label", it.label)
                put("url", it.url)
            }
            jsonArray.put(jasonObj)
        }

        return jsonArray.toString()
    }

    @TypeConverter
    fun toLink(source: String): List<Link> {
        val typeToken = object : TypeToken<List<Link>>() {}.type
        var jsonArray = Gson().fromJson<List<Link>>(source,typeToken)
        return jsonArray
    }
}