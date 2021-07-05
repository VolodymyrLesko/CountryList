package com.example.countrylist.base.views

interface BaseView<T> {
    fun setPresenter(presenter: T)
    fun hideProgressBar()
    fun showProgressBar()
    fun showError()
}