package com.example.countrylist.countriesList.contract

import com.example.countrylist.CountriesListQuery
import com.example.countrylist.base.presenters.BasePresenter
import com.example.countrylist.base.views.BaseView

interface MainActivityContract {
    interface Presenter : BasePresenter {
        fun onViewCreated()
        fun onLoadCountriesList()
    }

    interface View : BaseView<Presenter> {
        fun displayCountriesList(countriesList: List<CountriesListQuery.Country>)
    }
}