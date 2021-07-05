package com.example.countrylist.countryDetails.presenter

import com.example.countrylist.base.repository.implementation.CountryRepositoryImpl
import com.example.countrylist.countryDetails.contract.DetailsActivityContract
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers

class DetailsActivityPresenter(
    private var view: DetailsActivityContract.View?,
    private val countryRepository: CountryRepositoryImpl
) : DetailsActivityContract.Presenter {

    override fun onLoadCountryDetails(code: String) {
        view?.showProgressBar()
        countryRepository.getCountryDetails(code)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                view?.displayCountryDetails(it)
            }, {
                view?.hideProgressBar()
                view?.showError()
            }, {
                view?.hideProgressBar()
            })
    }

    override fun onDestroy() {
        this.view = null
    }

    fun attachView(view: DetailsActivityContract.View?) {
        this.view = view
    }

    fun detachView() {
        this.view = null
    }
}