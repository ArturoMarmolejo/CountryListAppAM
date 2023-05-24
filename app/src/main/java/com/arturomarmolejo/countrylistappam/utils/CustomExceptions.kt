package com.arturomarmolejo.countrylistappam.utils

class NullCountryListResponse(message: String = "Country List Response is NULL"): Exception(message)
class FailureResponse(message: String): Exception(message)