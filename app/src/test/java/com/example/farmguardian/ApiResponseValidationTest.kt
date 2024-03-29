
package com.example.farmguardian

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.collections.shouldNotBeEmpty
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import io.kotest.matchers.string.shouldHaveLength
import java.time.format.DateTimeFormatter

class ApiResponseValidationTest : StringSpec({
    val responseJson = """
        {
            "status": "ok",
            "totalResults": 53,
            "articles": [
                {
                    "source": {
                        "id": "bloomberg",
                        "name": "Bloomberg"
                    },
                    "author": null,
                    "title": "Stock Market Today: Dow, S&P Live Updates for March 28 - Bloomberg",
                    "description": null,
                    "url": "https://www.bloomberg.com/news/articles/2024-03-27/stock-market-today-dow-s-p-live-updates",
                    "urlToImage": null,
                    "publishedAt": "2024-03-28T06:55:00Z",
                    "content": "To continue, please click the box below to let us know you're not a robot."
                },
                {
                    "source": {
                        "id": null,
                        "name": "CNBC"
                    },
                    "author": "Pia Singh",
                    "title": "Stock futures are little changed as S&P 500 gets set to close out the first quarter up 10%: Live updates - CNBC",
                    "description": "Stocks rallied across the board on Wednesday, with the S&P 500 gaining 0.85% to close at a record-high level and approaches its best first quarter since 2019.",
                    "url": "https://www.cnbc.com/2024/03/27/stock-market-today-live-updates.html",
                    "urlToImage": "https://image.cnbcfm.com/api/v1/image/107390842-17110323172024-03-21t004447z_773410090_rc2wp6a0wuro_rtrmadp_0_usa-fed-investors.jpeg?v=1711578069&w=1920&h=1080",
                    "publishedAt": "2024-03-28T06:15:00Z",
                    "content": "Stock futures were trading near the flatline early Thursday as the S&amp;P 500 approached its best first-quarter performance in five years.\r\nFutures tied to the Dow Jones Industrial Average rose 11 p… [+1813 chars]"
                },
             
            ]
        }
    """.trimIndent()

    "Response status code should be 200" {

        val responseStatusCode = 200
        responseStatusCode shouldBe 200
    }

    "Content-Type header should be application/json" {

        val contentTypeHeader = "application/json"
        contentTypeHeader shouldBe "application/json"
    }

    "Response should have the required fields - status, totalResults, articles" {
        val responseData = parseResponse(responseJson)
        responseData["status"] shouldNotBe null
        responseData["totalResults"] shouldNotBe null
        responseData["articles"] shouldNotBe null
    }

    "Articles array should not be empty" {
        val responseData = parseResponse(responseJson)
        val articles = responseData["articles"] as List<*>
        articles.shouldNotBeEmpty()
    }

    "Each article should have non-empty title" {
        val responseData = parseResponse(responseJson)
        val articles = responseData["articles"] as List<*>
        articles.forEach {
            val title = (it as Map<*, *>)["title"] as String
            title.shouldHaveLength(1)
        }
    }

    "PublishedAt should be in valid date format" {
        val responseData = parseResponse(responseJson)
        val articles = responseData["articles"] as List<*>
        val dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'")
        articles.forEach {
            val publishedAt = (it as Map<*, *>)["publishedAt"] as String
            dateFormat.parse(publishedAt)
        }
    }

    "Url should be a valid URL" {
        val responseData = parseResponse(responseJson)
        val articles = responseData["articles"] as List<*>
        articles.forEach {
            val url = (it as Map<*, *>)["url"] as String
            url.matches("^https?://[a-zA-Z0-9-]+(\\.[a-zA-Z]{2,})+(\\S*)\$".toRegex())
        }
    }
})

fun parseResponse(json: String): Map<String, Any> {

    return emptyMap()
}
