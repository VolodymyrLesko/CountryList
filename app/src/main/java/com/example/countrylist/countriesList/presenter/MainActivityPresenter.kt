package com.example.countrylist.countriesList.presenter

import com.example.countrylist.base.repository.implementation.CountryRepositoryImpl
import com.example.countrylist.countriesList.contract.MainActivityContract
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers

class MainActivityPresenter(
    private var view: MainActivityContract.View?,
    private val countryRepository: CountryRepositoryImpl
) : MainActivityContract.Presenter {

    override fun onViewCreated() {
        onLoadCountriesList()
    }

    override fun onLoadCountriesList() {
        view?.showProgressBar()
        countryRepository.getCountryList()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                it.data?.let { it1 -> view?.displayCountriesList(it1.countries) }
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

    fun attachView(view: MainActivityContract.View?) {
        this.view = view
    }

    fun detachView() {
        this.view = null
    }
}