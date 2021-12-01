package com.example.countrylist.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.countrylist.GetCountryQuery
import com.example.countrylist.R
import com.example.countrylist.base.repository.implementation.CountryRepositoryImpl
import com.example.countrylist.countriesList.MainActivity
import com.example.countrylist.countryDetails.DetailsContract
import com.example.countrylist.countryDetails.DetailsPresenter

class DetailsFragment : Fragment(), DetailsContract.DetailsView {
    private var code: String? = null
    private val detailsPresenter = DetailsPresenter(this, CountryRepositoryImpl())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            code = it.getString("code")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_details, container, false)
        detailsPresenter.getCountryDetails(code!!, view)
        view.findViewById<Button>(R.id.btn_see_more).setOnClickListener {
            (context as MainActivity).navController.navigate(R.id.action_detailsFragment_to_seeMoreFragment)
        }
        return view
    }

    override fun displayCountryDetails(countryDetails: GetCountryQuery.Data, view: View) {
        countryDetails.country.apply {
            view.findViewById<TextView>(R.id.details_country_name).text = this?.name
            view.findViewById<TextView>(R.id.details_capital_name).text = this?.capital
            view.findViewById<TextView>(R.id.details_region_name).text = this?.continent?.name
            view.findViewById<TextView>(R.id.details_curency).text = this?.currency
        }
    }

    override fun showError(message: String) {
        Toast.makeText(
            context, message,
            Toast.LENGTH_LONG
        ).show()
    }
}