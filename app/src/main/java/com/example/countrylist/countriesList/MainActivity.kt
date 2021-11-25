package com.example.countrylist.countriesList

import android.content.Intent
import android.os.Bundle
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.countrylist.CountriesListQuery
import com.example.countrylist.MainFragment
import com.example.countrylist.R
import com.example.countrylist.base.constants.Utils
import com.example.countrylist.base.repository.implementation.CountryRepositoryImpl
import com.example.countrylist.countriesList.adapter.CountryAdapter
import com.example.countrylist.countryDetails.DetailsActivity
import com.example.countrylist.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(),
    MainContract.MainView {

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        openFragment(MainFragment.newInstance())
    }

    private fun openFragment(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.mainFrame, fragment, "main_fragment")
            .commit()
    }

    override fun startNewActivity(code: String) {
        intent = Intent(this, DetailsActivity::class.java).apply {
            putExtra(Utils.CODE, code)
        }
        startActivity(intent)
    }

    override fun showError(message: String) {
        Toast.makeText(
            this, message,
            Toast.LENGTH_LONG
        ).show()
    }
}
