package com.example.countrylist

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.countrylist.countriesList.adapter.CountryAdapter

class MainFragment : Fragment(), CountryAdapter.RVOnClickListener {
    private val dataModel: MainViewModel by viewModels()
    private lateinit var countryAdapter: CountryAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_main, container, false)
        dataModel.getCountriesList()
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        dataModel.countryList.observe(activity as LifecycleOwner, {
            initCountriesList(view)
        })
    }

    private fun initCountriesList(view: View) {
        val countryListRV = view.findViewById<RecyclerView>(R.id.countriesRV)
        countryAdapter = CountryAdapter(dataModel.countryList.value!!, this)
        countryListRV.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = countryAdapter
        }
    }

    override fun onClick(position: Int) {
        val fragment = DetailsFragment.newInstance(dataModel.countryList.value?.get(position)?.code!!)
        val transaction = activity?.supportFragmentManager!!.beginTransaction()
        transaction.hide(activity?.supportFragmentManager!!.findFragmentByTag("main_fragment")!!)
        transaction.add(R.id.mainFrame, fragment).addToBackStack(null).commit()
    }

    companion object {
        @JvmStatic
        fun newInstance() = MainFragment()
    }
}