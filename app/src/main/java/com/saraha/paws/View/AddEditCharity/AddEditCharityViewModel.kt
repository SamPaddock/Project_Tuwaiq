package com.saraha.paws.View.AddEditCharity

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class AddEditCharityViewModel: ViewModel() {

    val emailLiveData = MutableLiveData<String>()
    val nameLiveData = MutableLiveData<String>()
    val founderLiveData = MutableLiveData<String>()
    val mobileLiveData = MutableLiveData<String>()
    val facebookLinkLiveData = MutableLiveData<String>()
    val instagramLinkLiveData = MutableLiveData<String>()

    //Function to set data entered by user and set live data variables
    fun setCharityEmail(email: String){ emailLiveData.postValue(email) }
    fun setCharityName(name: String){ nameLiveData.postValue(name) }
    fun setCharityFounder(founder: String){ founderLiveData.postValue(founder) }
    fun setCharityMobile(mobile: String){ mobileLiveData.postValue(mobile) }
    fun setCharityFacebookLink(link: String){ facebookLinkLiveData.postValue(link) }
    fun setCharityInstagramLink(link: String){ instagramLinkLiveData.postValue(link) }
}