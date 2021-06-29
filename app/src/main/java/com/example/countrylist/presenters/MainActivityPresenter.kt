package com.example.countrylist.presenters

import com.example.countrylist.contracts.MainActivityContract
import com.example.countrylist.repository.implementation.CountryRepositoryImpl

class MainActivityPresenter(
    view: MainActivityContract.View,
    repository: CountryRepositoryImpl
) : MainActivityContract.Presenter {
    private val countryRepository = repository
    private var view: MainActivityContract.View? = view

    override fun onViewCreated() {
        onLoadCountriesList()
    }

    override fun onLoadCountriesList() {
        view?.displayCountriesList(countryRepository.getCountryList())
    }

    override fun onDestroy() {
        this.view = null
    }
}