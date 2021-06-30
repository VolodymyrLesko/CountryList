package com.example.countrylist.contracts

import com.example.countrylist.CountriesListQuery
import com.example.countrylist.presenters.BasePresenter
import com.example.countrylist.views.BaseView

interface MainActivityContract {
interface Presenter : BasePresenter {
        fun onViewCreated()
        fun onLoadCountriesList()
    }

    interface View : BaseView<Presenter> {
        fun displayCountriesList(countriesList: List<CountriesListQuery.Country>)
    }
}