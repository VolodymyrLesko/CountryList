package com.example.countrylist.contracts

import com.apollographql.apollo.api.Response
import com.example.countrylist.GetCountryQuery
import com.example.countrylist.presenters.BasePresenter
import com.example.countrylist.views.BaseView

interface DetailsActivityContract {
    interface Presenter : BasePresenter {
        fun onLoadCountryDetails(code: String)
    }

    interface View : BaseView<Presenter> {
        fun displayCountryDetails(countryDetails: Response<GetCountryQuery.Data>)
    }
}