package com.example.weatherreport.network.parsers

import com.example.weatherreport.network.WeatherApiService
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Test

class FourDayParserTest {

    private suspend fun fetchParsedApi(): FourDayParser {
        val resBody = WeatherApiService.getInstance().getFourDayForecast().body()!!
        return FourDayParser(resBody)
    }

    @Test
    fun getGeneralAvgTemperature_ValidTemperatureDayIndex() = runTest {
        val parser: FourDayParser = fetchParsedApi()

        // test valid day index
        assertNotNull(parser.getGeneralAvgTemperature(0))
        assertNotNull(parser.getGeneralAvgTemperature(1))
        assertNotNull(parser.getGeneralAvgTemperature(2))
        assertNotNull(parser.getGeneralAvgTemperature(3))

        // test invalid day index (outside boundary)
        assertThrows(IndexOutOfBoundsException::class.java) {
            parser.getGeneralAvgTemperature(-1)
        }
        assertThrows(IndexOutOfBoundsException::class.java) {
            parser.getGeneralAvgTemperature(4)
        }
    }

    @Test
    fun getGeneralAvgTemperature_ValidTemperatureRange() = runTest {
        val parser: FourDayParser = fetchParsedApi()

        val temperature1 = parser.getGeneralAvgTemperature(0)
        assertFalse(temperature1 > 100)
        assertFalse(temperature1 < 0)

        val temperature2 = parser.getGeneralAvgTemperature(1)
        assertFalse(temperature2 > 100)
        assertFalse(temperature2 < 0)

        val temperature3 = parser.getGeneralAvgTemperature(2)
        assertFalse(temperature3 > 100)
        assertFalse(temperature3 < 0)

        val temperature4 = parser.getGeneralAvgTemperature(3)
        assertFalse(temperature4 > 100)
        assertFalse(temperature4 < 0)
    }
}
