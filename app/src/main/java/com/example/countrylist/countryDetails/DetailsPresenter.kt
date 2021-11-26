package com.example.countrylist.countryDetails

import android.view.View
import com.example.countrylist.base.constants.Utils
import com.example.countrylist.base.repository.implementation.CountryRepositoryImpl
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers

class DetailsPresenter(
    private var detailsView: DetailsContract.DetailsView?,
    private val countryRepository: CountryRepositoryImpl
) : DetailsContract.Presenter {

    override fun getCountryDetails(code: String, view: View) {
        countryRepository.getCountryDetails(code)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                detailsView?.displayCountryDetails(it.data!!, view)
            }, {
                detailsView?.showError(Utils.ERROR_MESSAGE)
            }, {
            })
    }

    override fun attachView(view: DetailsContract.DetailsView) {
        this.detailsView = view
    }

    override fun detachView() {
        this.detailsView = null
    }
}