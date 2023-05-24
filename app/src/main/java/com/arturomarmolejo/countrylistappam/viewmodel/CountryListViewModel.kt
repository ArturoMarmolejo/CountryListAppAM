package com.arturomarmolejo.countrylistappam.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arturomarmolejo.countrylistappam.model.domain.CountryDomain
import com.arturomarmolejo.countrylistappam.rest.CountryRepository
import com.arturomarmolejo.countrylistappam.utils.UIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * [CountryListViewModel] -
 * Defines the ViewModel for the application
 * Contains the information that may be shared between different fragments
 * @param countryRepository repository to get the country information from the API
 * @param ioDispatcher Coroutine dispatcher where the background operations will be executed
 */
@HiltViewModel
class CountryListViewModel @Inject constructor(
    private val countryRepository: CountryRepository,
    private val ioDispatcher: CoroutineDispatcher
): ViewModel() {

    private var isInitialized = false

    lateinit var selectedCountryItem: CountryDomain
    private val _allCountryList: MutableLiveData<UIState<MutableList<CountryDomain>>> =
        MutableLiveData(UIState.LOADING)
    val allCountryList: LiveData<UIState<MutableList<CountryDomain>>> get() = _allCountryList

    init {
        if(!isInitialized){
            getAllCountries()
            isInitialized = true
        }
    }

    fun getAllCountries() {
        viewModelScope.launch(ioDispatcher) {
            countryRepository.getCountryList().collect{ result ->
                _allCountryList.postValue(result)
            }
        }
    }

}