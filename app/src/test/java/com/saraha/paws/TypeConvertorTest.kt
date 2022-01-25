package com.saraha.paws

import com.saraha.paws.Database.DataTypeConverter
import com.saraha.paws.Model.Facts.Data
import org.junit.Test
import org.junit.Assert.*


class TypeConvertorTest {

    @Test
    fun dataTypeConverter(): Unit {
        val source =
            listOf(Data("Cats have about 130,000 hairs per square inch (20,155 hairs per square centimeter)",
                83))
        val fromData = DataTypeConverter().fromData(source)
        assertNotNull(fromData)
    }

    @Test
    fun dataTypeConverterTo(): Unit {
        assertNotNull(DataTypeConverter().toData("Cats have about 130,000 hairs per square inch (20,155 hairs per square centimeter),83"))
    }
}