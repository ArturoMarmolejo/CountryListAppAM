package com.arturomarmolejo.countrylistappam.di

import com.arturomarmolejo.countrylistappam.rest.CountryRepository
import com.arturomarmolejo.countrylistappam.rest.CountryRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class RepositoryModule {
    @Binds
    abstract fun providesCountryRepositoryImpl(countryRepositoryImpl: CountryRepositoryImpl):
            CountryRepository
}