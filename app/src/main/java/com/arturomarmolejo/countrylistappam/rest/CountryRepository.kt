package com.arturomarmolejo.countrylistappam.rest

import com.arturomarmolejo.countrylistappam.model.apiresponse.CountryListResponseItem
import com.arturomarmolejo.countrylistappam.model.domain.CountryDomain
import com.arturomarmolejo.countrylistappam.model.domain.toCountryDomain
import com.arturomarmolejo.countrylistappam.utils.FailureResponse
import com.arturomarmolejo.countrylistappam.utils.NullCountryListResponse
import com.arturomarmolejo.countrylistappam.utils.UIState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

/**
 * [CountryRepository] -
 * Defines the methods to get the response from the API
 */
interface CountryRepository {
    /**
     * [getCountryList] -
     * Get Country List information from API

     */
    fun getCountryList(): Flow<UIState<MutableList<CountryDomain>>>
}
/**
 * [CountryRepositoryImpl] -
 * Implementation of [CountryRepository] interface
 */
class CountryRepositoryImpl @Inject constructor(
    private val countryServiceApi: CountryServiceApi
): CountryRepository {
    override fun getCountryList(): Flow<UIState<MutableList<CountryDomain>>> = flow {
        emit(UIState.LOADING)
        try {
            val response = countryServiceApi.getAllCountries()
            if (response.isSuccessful) {
                response.body()?.let {
                    emit(UIState.SUCCESS(it.toCountryDomain()))
                } ?: throw Exception(NullCountryListResponse())
            } else {
                throw FailureResponse(response.errorBody().toString())
            }
        } catch (e: Exception) {
            emit(UIState.ERROR(e))
        }
    }
}