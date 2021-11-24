package com.example.countrylist

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.apollographql.apollo.api.Response
import com.example.countrylist.base.constants.Utils
import com.example.countrylist.base.repository.implementation.CountryRepositoryImpl
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers

open class MainViewModel : ViewModel() {
    val countryList: MutableLiveData<List<CountriesListQuery.Country>> by lazy {
        MutableLiveData()
    }
    val countryDetails: MutableLiveData<Response<GetCountryQuery.Data>> by lazy {
        MutableLiveData()
    }
    private val countryRepository = CountryRepositoryImpl()

    fun getCountriesList() {
        countryRepository.getCountryList()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                it.data?.let { it1 -> countryList.postValue(it1.countries) }
            }, { }, { })
    }

    fun getCountryDetails(code: String) {
        countryRepository.getCountryDetails(code)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                countryDetails.postValue(it)
            }, { }, { })
    }
}