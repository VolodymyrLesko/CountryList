package com.example.countrylist

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.viewModels
import androidx.lifecycle.LifecycleOwner
import com.apollographql.apollo.api.Response

private const val ARG_PARAM1 = "param1"

class DetailsFragment : Fragment() {
    private var param1: String? = null
    private val dataModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
        }
        dataModel.getCountryDetails(param1!!)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_details, container, false)
        dataModel.countryDetails.observe(activity as LifecycleOwner, {
            displayCountryDetails(it, view)
        })
        return view
    }

    private fun displayCountryDetails(countryDetails: Response<GetCountryQuery.Data>, view: View) {
        countryDetails.data?.country.apply {
            view.findViewById<TextView>(R.id.details_country_name).text = this?.name
            view.findViewById<TextView>(R.id.details_capital_name).text = this?.capital
            view.findViewById<TextView>(R.id.details_region_name).text = this?.continent?.name
            view.findViewById<TextView>(R.id.details_curency).text = this?.currency
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String) =
            DetailsFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                }
            }
    }
}