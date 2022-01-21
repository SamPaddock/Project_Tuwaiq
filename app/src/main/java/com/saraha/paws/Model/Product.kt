package com.saraha.paws.Model

import java.io.Serializable

class Product (
    var pid: String,
    var vid: String,
    var name: String,
    var description: String,
    var weight: Double? = null,
    var price: Double,
    var quantity: Int? = null,
    var category: String,
    var photo: String,
    var type: String
        ): Serializable
{
    fun serviceType(): List<String>{
        return listOf("Service", "Product")
    }
    fun categoryProductType(): List<String>{
        return listOf("Food", "Toy", "Accessories", "Grooming", "Treats", "Litter", "Other")
    }
    fun categoryVetServiceType(): List<String>{
        return listOf("Hotel", "Medical", "Other")
    }
    fun categoryGroomingServiceType(): List<String>{
        return listOf("Full Service", "Bath", "Trim", "Brush", "Bath & Trim", "Bath and Brush", "Nails")
    }
}