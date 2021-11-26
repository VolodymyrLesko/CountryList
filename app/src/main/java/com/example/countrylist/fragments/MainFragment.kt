package com.example.countrylist.fragments

import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.countrylist.CountriesListQuery
import com.example.countrylist.R
import com.example.countrylist.base.repository.implementation.CountryRepositoryImpl
import com.example.countrylist.countriesList.MainActivity
import com.example.countrylist.countriesList.MainContract
import com.example.countrylist.countriesList.MainPresenter
import com.example.countrylist.countriesList.adapter.CountryAdapter

class MainFragment : Fragment(), CountryAdapter.RVOnClickListener,
    MainContract.MainView {
    private var countryList: List<CountriesListQuery.Country> = ArrayList()
    private lateinit var countryAdapter: CountryAdapter
    private val mainPresenter = MainPresenter(this, CountryRepositoryImpl())


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_main, container, false)
        initCountriesList(view)
        mainPresenter.getCountriesList()
        return view
    }

    private fun initCountriesList(view: View) {
        val countryListRV = view.findViewById<RecyclerView>(R.id.countriesRV)
        countryAdapter = CountryAdapter(countryList, this)
        countryListRV.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = countryAdapter
        }
    }

    override fun onClick(position: Int) {
        val fragment =
            DetailsFragment.newInstance(countryAdapter.countriesList[position].code)
        val transaction = activity?.supportFragmentManager!!.beginTransaction()
        if ((context as MainActivity).resources.configuration.orientation == ActivityInfo.SCREEN_ORIENTATION_PORTRAIT) {
            transaction.hide(activity?.supportFragmentManager!!.findFragmentByTag("main_fragment")!!)
            transaction.add(R.id.listFrame, fragment).addToBackStack(null).commit()
        } else {
            (context as MainActivity).binding.bannerView?.visibility = View.GONE
            transaction.replace(R.id.detailsFrame, fragment).addToBackStack(null).commit()
        }
    }

    override fun showError(message: String) {
        Toast.makeText(
            context, message,
            Toast.LENGTH_LONG
        ).show()
    }

    override fun displayCountriesList(countriesList: List<CountriesListQuery.Country>) {
        this.countryAdapter.setList(countriesList as MutableList<CountriesListQuery.Country>)
    }

    companion object {
        @JvmStatic
        fun newInstance() = MainFragment()
    }

}