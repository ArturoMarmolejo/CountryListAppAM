package com.arturomarmolejo.countrylistappam.views

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.arturomarmolejo.countrylistappam.R
import com.arturomarmolejo.countrylistappam.databinding.FragmentCountryListBinding
import com.arturomarmolejo.countrylistappam.model.domain.CountryDomain
import com.arturomarmolejo.countrylistappam.utils.BaseFragment
import com.arturomarmolejo.countrylistappam.utils.UIState
import com.arturomarmolejo.countrylistappam.views.adapter.CountryListAdapter


/**
 * A simple [Fragment] subclass.
 * Use the [CountryListFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
private const val TAG = "CountryListFragment"
class CountryListFragment : BaseFragment() {

    private val binding by lazy {
        FragmentCountryListBinding.inflate(layoutInflater)
    }

    private val countryListAdapter: CountryListAdapter by lazy {
        CountryListAdapter {
            countryListViewModel.selectedCountryItem = it
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val countryList: MutableList<CountryDomain> = mutableListOf()

        binding.rvCountryList.apply {
            layoutManager = LinearLayoutManager(
                requireContext(),
                LinearLayoutManager.VERTICAL,
                false
            )
            setHasFixedSize(true)
            adapter = countryListAdapter
        }

        countryListViewModel.allCountryList.observe(viewLifecycleOwner) {state ->
            when(state) {
                is UIState.LOADING -> {}
                is UIState.SUCCESS -> {
                    countryList.clear()
                    countryList.addAll(state.response)
                    countryListAdapter.updateItems(state.response)
                }
                is UIState.ERROR -> {
                    Log.d(TAG, "onCreateView: UIState Error: $state")
                }
            }
        }

        binding.svCountryList.setOnQueryTextListener(object: SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                val filteredList = countryList.filter {country ->
                    country.name.contains(newText.toString(), true)
                }.toMutableList()
                Log.d(TAG, "onQueryTextChange: ${filteredList.size}")
                countryListAdapter.updateItems(filteredList)

                return true
            }
        })

        return binding.root
    }


}