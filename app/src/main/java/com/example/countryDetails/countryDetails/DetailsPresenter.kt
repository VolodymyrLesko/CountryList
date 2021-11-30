package com.example.countryDetails.countryDetails

import com.example.countryDetails.base.constants.Utils
import com.example.countryDetails.base.repository.implementation.CountryRepositoryImpl
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers

class DetailsPresenter(
    private var detailsView: DetailsContract.DetailsView?,
    private val countryRepository: CountryRepositoryImpl
) : DetailsContract.Presenter {

    override fun getCountryDetails(code: String) {
        detailsView?.showProgressBar()
        countryRepository.getCountryDetails(code)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                detailsView?.displayCountryDetails(it)
            }, {
                detailsView?.hideProgressBar()
                detailsView?.showError(Utils.ERROR_MESSAGE)
            }, {
                detailsView?.hideProgressBar()
            })
    }

    override fun attachView(view: DetailsContract.DetailsView) {
        this.detailsView = view
    }

    override fun detachView() {
        this.detailsView = null
    }
}