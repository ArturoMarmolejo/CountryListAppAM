package com.arturomarmolejo.countrylistappam.rest

import com.arturomarmolejo.countrylistappam.model.apiresponse.CountryListResponseItem
import com.arturomarmolejo.countrylistappam.rest.CountryServiceApi.Companion.COUNTRY_LIST
import retrofit2.Response
import retrofit2.http.GET

/**
 * [ServiceApi] -
 * Defines methods to call the API with Retrofit
 */

interface CountryServiceApi {

    /**
     * [getAllCountries] -
     * Get Country LiST information from the API
     * @return Response<WeatherResponse>
     */

    @GET(COUNTRY_LIST)
    suspend fun getAllCountries(): Response<MutableList<CountryListResponseItem>>

    companion object {
        //https://gist.githubusercontent.com/peymano-wmt/32dcb892b06648910ddd40406e37fdab/raw/db25946fd77c5873b0303b858e861ce724e0dcd0/countries.json
        const val BASE_URL = "https://gist.githubusercontent.com/"
        private const val PATH_PREFIX = "peymano-wmt/32dcb892b06648910ddd40406e37fdab"
        private const val FILE_NAME = "countries.json"
        private const val COUNTRY_LIST = "$PATH_PREFIX/raw/db25946fd77c5873b0303b858e861ce724e0dcd0/$FILE_NAME"
    }
}