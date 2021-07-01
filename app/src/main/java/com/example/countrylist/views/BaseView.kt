package com.example.countrylist.views

interface BaseView<T> {
    fun setPresenter(presenter: T)
    fun hideProgressBar()
}