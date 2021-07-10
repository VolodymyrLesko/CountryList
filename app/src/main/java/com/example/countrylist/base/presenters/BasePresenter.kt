package com.example.countrylist.base.presenters

interface BasePresenter<T> {
    fun attachView(view: T)
    fun detachView()
}