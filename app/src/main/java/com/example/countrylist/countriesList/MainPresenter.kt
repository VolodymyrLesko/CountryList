package com.example.countrylist.countriesList

import androidx.annotation.VisibleForTesting
import com.example.countrylist.CountriesListQuery
import com.example.countrylist.base.constants.Utils
import com.example.countrylist.base.repository.CountryRepository
import com.example.countrylist.countriesList.adapter.CountryAdapter
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers

class MainPresenter(
    private var countryRepository: CountryRepository
) : MainContract.Presenter, CountryAdapter.RVOnClickListener {
    private var mainView: MainContract.MainView? = null
    private lateinit var countriesList: List<CountriesListQuery.Country>

    override fun getCountriesList() {
        mainView?.showProgressBar()
        countryRepository.getCountryList()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                it.data?.let { it1 -> countriesList = it1.countries }
                mainView?.displayCountriesList(countriesList)
            }, {
                mainView?.hideProgressBar()
                mainView?.showError(Utils.ERROR_MESSAGE)
            }, {
                mainView?.hideProgressBar()
            })
    }

    override fun attachView(view: MainContract.MainView) {
        this.mainView = view
    }

    override fun detachView() {
        this.mainView = null
    }

    override fun onClick(position: Int) {
        mainView?.startNewActivity(countriesList[position].code)
    }

    fun setRepository(repository: CountryRepository) {
        this.countryRepository = repository
    }
}