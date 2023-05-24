package com.arturomarmolejo.countrylistappam.rest

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.arturomarmolejo.countrylistappam.utils.UIState
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test


class CountryRepositoryImplTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    private lateinit var testObject: CountryRepository
    private val mockApi = mockk<CountryServiceApi>(relaxed = true)

    private val testDispatcher = UnconfinedTestDispatcher()
    private val testScope = TestScope(testDispatcher)


    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        testObject = CountryRepositoryImpl(mockApi)
    }


    @After
    fun tearDown() {
        Dispatchers.resetMain()
        clearAllMocks()
    }

    @Test
    fun `getAllCountries get LIST OF COUNTRIES when server returns SUCCESS STATE`() {
        //AAA
        //assignment
        coEvery { mockApi.getAllCountries() } returns mockk {
            every { isSuccessful } returns true
            every { body() } returns mutableListOf(mockk(), mockk(), mockk())
        }
        //action
        val job = testScope.launch {
            testObject.getCountryList().collect{
                if(it is UIState.SUCCESS) {
                    //assertion
                    assert(it is UIState.SUCCESS)
                    assertEquals(3, it.response.size)
                }
            }
        }

        job.cancel()
    }

    @Test
    fun `getAllCountries get NULL when server returns an ERROR STATE`() {
        //AAA
        //assignment
        coEvery { mockApi.getAllCountries() } returns mockk {
            every { isSuccessful } returns true
            every { body() } returns null
        }
        //action
        val job = testScope.launch {
            testObject.getCountryList().collect{
                if(it is UIState.ERROR) {
                    //assertion
                    assert(it is UIState.ERROR)
                    assertEquals("Country List Response is NULL", it.error)
                }
            }
        }

        job.cancel()
    }

    @Test
    fun `getAllCountries get FAILURE RESPONSE when server returns an ERROR STATE`() {
        //AAA
        //assignment
        coEvery { mockApi.getAllCountries() } returns mockk {
            every { isSuccessful } returns false
            every { errorBody() } returns mockk {
                every { string() } returns "ERROR"
            }
        }
        //action
        val job = testScope.launch {
            testObject.getCountryList().collect{
                if(it is UIState.ERROR) {
                    //assertion
                    assert(it is UIState.ERROR)
                    assertEquals("Country List Response ERROR", it.error)
                }
            }
        }

        job.cancel()
    }

    @Test
    fun `getAllCountries get EXCEPTION when server returns an ERROR STATE`() {
        //AAA
        //assignment
        coEvery { mockApi.getAllCountries() } returns mockk {
            every { isSuccessful } throws Exception("Error: Exception")
            every { errorBody() } returns mockk {
                every { string() } returns "ERROR"
            }
        }
        //action
        val job = testScope.launch {
            testObject.getCountryList().collect{
                if(it is UIState.ERROR) {
                    //assertion
                    assert(it is UIState.ERROR)
                    assertEquals("Country List Response EXCEPTION", it.error)
                }
            }
        }

        job.cancel()
    }


}