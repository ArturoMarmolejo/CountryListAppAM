package com.arturomarmolejo.countrylistappam.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.arturomarmolejo.countrylistappam.rest.CountryRepository
import com.arturomarmolejo.countrylistappam.utils.UIState
import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test


internal class CountryListViewModelTest {
    @get:Rule val rule = InstantTaskExecutorRule()

    private lateinit var testObject: CountryListViewModel

    private val mockRepository = mockk<CountryRepository>(relaxed = true)
    private val mockDispatcher = UnconfinedTestDispatcher()


    @Before
    fun setUp() {
        Dispatchers.setMain(mockDispatcher)
        testObject = CountryListViewModel(mockRepository, mockDispatcher)
    }


    @After
    fun tearDown() {
        Dispatchers.resetMain()
        clearAllMocks()
    }

    @Test
    fun `getAllCountries GET ALL COUNTRIES when repository returns SUCCESS state`() {
        every { mockRepository.getCountryList() } returns flowOf(
            UIState.SUCCESS(mutableListOf(mockk(), mockk(), mockk()))
        )
        testObject.allCountryList.observeForever {
            if(it is UIState.SUCCESS) {
                assert(it is UIState.SUCCESS)
                assertEquals(3, it.response.size)
            }
        }

        testObject.getAllCountries()

    }

    @Test
    fun `getAllCountries GET FAILURE RESPONSE when repository returns ERROR state`() {
        every { mockRepository.getCountryList() } returns flowOf(
            UIState.ERROR(Exception("Error"))
        )
        testObject.allCountryList.observeForever {
            if(it is UIState.ERROR) {
                assert(it is UIState.ERROR)
                assertEquals("Error", it.error)
            }
        }

        testObject.getAllCountries()

    }


}