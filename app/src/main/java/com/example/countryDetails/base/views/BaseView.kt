package com.example.countryDetails.base.views

interface BaseView {
    fun hideProgressBar()
    fun showProgressBar()
    fun showError(message: String)
}