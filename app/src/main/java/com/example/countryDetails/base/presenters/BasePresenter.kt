package com.example.countryDetails.base.presenters

interface BasePresenter<T> {
    fun attachView(view: T)
    fun detachView()
}