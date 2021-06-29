package com.example.countrylist.contracts

import com.example.countrylist.CountriesListQuery
import com.example.countrylist.GetCountryQuery
import com.example.countrylist.presenters.BasePresenter
import com.example.countrylist.views.BaseView
import io.reactivex.rxjava3.core.Single

interface DetailsActivityContract {
    interface Presenter : BasePresenter {
        fun onLoadCountryDetails(code: String)
    }

    interface View : BaseView<Presenter> {
        fun displayCountryDetails(countryDetails: Single<GetCountryQuery.Data>)
    }
}