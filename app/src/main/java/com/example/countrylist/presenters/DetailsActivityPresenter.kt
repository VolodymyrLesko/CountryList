package com.example.countrylist.presenters

import com.example.countrylist.contracts.DetailsActivityContract
import com.example.countrylist.repository.implementation.CountryRepositoryImpl
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers

class DetailsActivityPresenter(
    view: DetailsActivityContract.View,
    repository: CountryRepositoryImpl
) : DetailsActivityContract.Presenter {
    private val countryRepository = repository
    private var view: DetailsActivityContract.View? = view

    override fun onLoadCountryDetails(code: String) {
        countryRepository.getCountryDetails(code)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                view?.displayCountryDetails(it)
            }, {
                it.printStackTrace()
            })
    }

    override fun onDestroy() {
        this.view = null
    }
}