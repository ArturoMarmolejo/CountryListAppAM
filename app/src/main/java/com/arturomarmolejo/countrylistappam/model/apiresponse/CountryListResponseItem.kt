package com.arturomarmolejo.countrylistappam.model.apiresponse


import com.google.gson.annotations.SerializedName

data class CountryListResponseItem(
    @SerializedName("capital")
    val capital: String = "",
    @SerializedName("code")
    val code: String = "",
    @SerializedName("currency")
    val currency: Currency = Currency(),
    @SerializedName("demonym")
    val demonym: String? = null,
    @SerializedName("flag")
    val flag: String = "",
    @SerializedName("language")
    val language: Language = Language(),
    @SerializedName("name")
    val name: String = "",
    @SerializedName("region")
    val region: String = ""
)