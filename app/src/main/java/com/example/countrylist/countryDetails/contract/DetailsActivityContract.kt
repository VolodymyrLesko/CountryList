package com.example.countrylist.countryDetails.contract

import com.apollographql.apollo.api.Response
import com.example.countrylist.GetCountryQuery
import com.example.countrylist.base.presenters.BasePresenter
import com.example.countrylist.base.views.BaseView

interface DetailsActivityContract {
    interface Presenter : BasePresenter {
        fun onLoadCountryDetails(code: String)
    }

    interface View : BaseView<Presenter> {
        fun displayCountryDetails(countryDetails: Response<GetCountryQuery.Data>)
    }
}