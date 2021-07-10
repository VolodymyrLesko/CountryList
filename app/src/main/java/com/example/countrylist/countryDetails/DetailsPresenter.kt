package com.example.countrylist.countryDetails

import com.example.countrylist.base.constants.Utils
import com.example.countrylist.base.repository.CountryRepository
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers

class DetailsPresenter(
    private val countryRepository: CountryRepository
) : DetailsContract.Presenter {
    private var detailsView: DetailsContract.DetailsView? = null
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