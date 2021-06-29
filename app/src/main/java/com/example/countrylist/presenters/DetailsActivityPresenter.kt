package com.example.countrylist.presenters

import com.example.countrylist.contracts.DetailsActivityContract
import com.example.countrylist.repository.implementation.CountryRepositoryImpl

class DetailsActivityPresenter(
    view: DetailsActivityContract.View,
    repository: CountryRepositoryImpl
) : DetailsActivityContract.Presenter {
    private val countryRepository = repository
    private var view: DetailsActivityContract.View? = view

    override fun onLoadCountryDetails(code: String) {
        view?.displayCountryDetails(countryRepository.getCountryDetails(code))
    }

    override fun onDestroy() {
        this.view = null
    }
}