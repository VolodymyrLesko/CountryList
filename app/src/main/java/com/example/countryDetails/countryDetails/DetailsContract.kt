package com.example.countryDetails.countryDetails

import com.apollographql.apollo.api.Response
import com.example.countryDetails.base.presenters.BasePresenter
import com.example.countryDetails.base.views.BaseView
import com.example.countrylist.GetCountryQuery

interface DetailsContract {
    interface Presenter : BasePresenter<DetailsView> {
        fun getCountryDetails(code: String)
    }

    interface DetailsView : BaseView {
        fun displayCountryDetails(countryDetails: Response<GetCountryQuery.Data>)
    }
}