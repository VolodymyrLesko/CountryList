package com.example.countrylist.countriesList

import com.example.countrylist.CountriesListQuery
import com.example.countrylist.base.presenters.BasePresenter
import com.example.countrylist.base.views.BaseView

interface MainContract {
    interface Presenter : BasePresenter<MainView> {
        fun getCountriesList()
    }

    interface MainView : BaseView {
        fun displayCountriesList(countriesList: List<CountriesListQuery.Country>)
        fun startNewActivity(code: String)
    }
}