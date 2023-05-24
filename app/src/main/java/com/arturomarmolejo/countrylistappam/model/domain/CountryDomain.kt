package com.arturomarmolejo.countrylistappam.model.domain


import com.arturomarmolejo.countrylistappam.model.apiresponse.CountryListResponseItem
import com.arturomarmolejo.countrylistappam.model.apiresponse.Currency
import com.arturomarmolejo.countrylistappam.model.apiresponse.Language

/**
 * [CountryDomain] -
 * Defines the data that will be saved from the Country List API
 * @param capital the capital for a specific country
 * @param code the corresponding country code
 * @param currency the currency for that country
 * @param demonym the demonym (population designation) for a country
 * @param flag the corresponding flag for that country
 * @param language the languages for that country
 * @param name the country name
 * @param name the region from that country
 */

data class CountryDomain(
    val capital: String,
    val code: String,
    val currency: Currency,
    val demonym: String?,
    val flag: String,
    val language: Language,
    val name: String,
    val region: String,
)

/**
 * [toCountryDomain] -
 * Maps the API response [CountryListResponse] to its domain [CountryDomain]
 * @return information mapped to the domain
 */

fun MutableList<CountryListResponseItem>.toCountryDomain(): MutableList<CountryDomain> =
    (this?.map {
        CountryDomain(
            capital = it.capital,
            code = it.code,
            currency = (it.currency ?: Currency(code = "", name = "", symbol = "")),
            demonym = it.demonym,
            flag = it.flag,
            language = (it.language ?: Language(code = "", iso6392 = "", name = "", nativeName = "")),
            name = it.name,
            region = it.region
        )
    } ?: emptyList()) as MutableList<CountryDomain>
