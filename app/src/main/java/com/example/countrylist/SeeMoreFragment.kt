package com.example.countrylist

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.example.countrylist.countriesList.MainActivity

class SeeMoreFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_see_more, container, false)
        view.findViewById<Button>(R.id.btn_to_list).setOnClickListener {
            (context as MainActivity).navController.navigate(R.id.action_seeMoreFragment_to_mainFragment)
        }
        return view
    }
}