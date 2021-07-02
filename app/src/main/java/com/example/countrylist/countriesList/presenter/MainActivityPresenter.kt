package com.example.countrylist.countriesList.presenter

import com.example.countrylist.countriesList.contract.MainActivityContract
import com.example.countrylist.base.repository.implementation.CountryRepositoryImpl
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers

class MainActivityPresenter(
    view: MainActivityContract.View,
    repository: CountryRepositoryImpl
) : MainActivityContract.Presenter {
    private val countryRepository = repository
    private var view: MainActivityContract.View? = view

    override fun onViewCreated() {
        onLoadCountriesList()
    }

    override fun onLoadCountriesList() {
        countryRepository.getCountryList()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                it.data?.let { it1 -> view?.displayCountriesList(it1.countries) }
            }, {
                it.printStackTrace()
            }, {
                view?.hideProgressBar()
            })
    }

    override fun onDestroy() {
        this.view = null
    }
}