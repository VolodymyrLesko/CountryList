package com.example.countrylist.base.views

interface BaseView {
    fun hideProgressBar()
    fun showProgressBar()
    fun showError(message: String)
}