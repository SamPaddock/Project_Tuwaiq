package com.saraha.paws.Util

class Helper {

    //Function returns static dropdown menu list
    fun getStatusList(): List<String>{
        return listOf("", "Adopted", "Fostered", "For Adoption", "Vet: pre-check"
            , "Vet: post-check", "Vet: post-surgery", "Action: Rescue")
    }

    //Function returns static dropdown menu list
    fun getTypeList(): List<String>{
        return listOf("", "Cat", "Dog", "Bird", "Aqua", "Rabbit", "Farm Animal", "Other")
    }

}