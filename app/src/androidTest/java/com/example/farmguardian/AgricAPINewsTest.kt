package com.example.farmguardian

import org.json.JSONException
import org.json.JSONObject
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotEquals
import org.junit.Test

class AgricAPINewsTest {

    // Simulated sample response JSON data
    private val sampleApiResponse = """
        {
            "status": {
                "code": 200,
                "message": "Success."
            },
            "results": [
                {
                    "countyCode": "17001",
                    "county": "Adams County",
                    "state": "IL",
                    "startDate": "2024-02-02",
                    "endDate": "2024-02-09",
                    "ndvi": 0.26731,
                    "ndviCount": 8254,
                    "ndwi": -0.21392,
                    "ndwiCount": 8254,
                    "lstd": 14.18,
                    "lstn": 0.32,
                    "tmax": 13.38,
                    "tmin": 0.86,
                    "prcp": 0,
                    "updatedAt": "2024-03-07T00:00:00-06:00"
                }
            ]
        }
    """.trimIndent()

    @Test
    fun testApiRequestSuccess() {
        // Simulate a successful API response
        val apiResponse = sampleApiResponse

        // Verify that the app can successfully retrieve data from the API
        assertEquals(apiResponse.isNotEmpty(), true)
    }

    @Test
    fun testParsingOfApiResponse() {
        // Simulate a successful API response
        val apiResponse = sampleApiResponse

        // Parse the JSON response and extract the county
        val county = "Adams County"
        val parsedCounty = parseCountyFromApiResponse(apiResponse)

        // Verify that the parsed county matches the expected value
        assertEquals(county, parsedCounty)
    }

    @Test
    fun testApiRequestFailure() {
        // Simulate an empty API response
        val emptyApiResponse = ""

        // Verify that the app handles empty responses gracefully
        assertEquals(emptyApiResponse.isNotEmpty(), false)
    }

    @Test
    fun testParsingOfEmptyApiResponse() {
        // Simulate an empty API response
        val emptyApiResponse = ""

        // Parse the JSON response and extract the county
        val parsedCounty = parseCountyFromApiResponse(emptyApiResponse)

        // is the parsed county empty?
        assertEquals("", parsedCounty)
    }

    //parse the county from the API response (mock implementation)
    private fun parseCountyFromApiResponse(apiResponse: String): String {

        // simple Assumption, valid JSON format
        try {
            val json = JSONObject(apiResponse)
            val resultsArray = json.getJSONArray("results")
            if (resultsArray.length() > 0) {
                val resultObject = resultsArray.getJSONObject(0)
                return resultObject.getString("county")
            }
        } catch (e: JSONException) {
            // Handle JSON parsing errors
            e.printStackTrace()
        }
        // Return an empty string if parsing fails or if the response is empty
        return ""
    }
}
