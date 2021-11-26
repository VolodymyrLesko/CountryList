package com.example.countrylist.countryDetails

import android.view.View
import com.apollographql.apollo.api.Response
import com.example.countrylist.GetCountryQuery
import com.example.countrylist.base.presenters.BasePresenter
import com.example.countrylist.base.views.BaseView

interface DetailsContract {
    interface Presenter : BasePresenter<DetailsView> {
        fun getCountryDetails(code: String, view: View)
    }

    interface DetailsView : BaseView {
        fun displayCountryDetails(countryDetails: GetCountryQuery.Data, view: View)
    }
}