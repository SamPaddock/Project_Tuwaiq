package com.saraha.paws.Model

class Product (
    var pid: String,
    var vid: String,
    var name: String,
    var description: String,
    var weight: Double? = null,
    var price: Double,
    var quantity: Int? = null,
    var category: String,
    var type: String
        )
{
    fun serviceType(): List<String>{
        return listOf("Service", "Product")
    }
    fun categoryType(): List<String>{
        return listOf("Food", "Toy", "Accessories", "Grooming", "Treats", "Litter", "Other")
    }
    fun categoryVetType(): List<String>{
        return listOf("Hotel", "Medical", "Other")
    }
    fun categoryGroomingType(): List<String>{
        return listOf("Full Service", "Bath", "Trim", "Brush", "Bath & Trim", "Bath and Brush", "Nails")
    }
}