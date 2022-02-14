package com.backbase.assignment.ui.viewmodel.data.api


import com.backbase.assignment.ui.data.api.TMDBService
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okio.buffer
import okio.source
import org.junit.After
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class NewsAPIServiceTest {

    private lateinit var service: TMDBService
    private lateinit var server: MockWebServer

    @Before
    fun setUp() {
        server = MockWebServer()
        service = Retrofit.Builder()
            .baseUrl(server.url(""))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(TMDBService::class.java)


    }

    private fun enqueueMockResponse(
        fileName: String
    ) {
        val inputStream = javaClass.classLoader!!.getResourceAsStream(fileName)
        val source = inputStream.source().buffer()
        val mockResponse = MockResponse()
        mockResponse.setBody(source.readString(Charsets.UTF_8))
        server.enqueue(mockResponse)

    }

    @Test
    fun getMoviesNowPlaying_receivedExpected() {
        runBlocking {
            enqueueMockResponse("movielist.json")
            val responseBody =
                service.getMoviesNowPlaying("55957fcf3ba81b137f8fc01ac5a31fb5", "1").body()
            val request = server.takeRequest()
            assertThat(responseBody).isNotNull()
            assertThat(request.path).isEqualTo("/movie/now_playing?api_key=55957fcf3ba81b137f8fc01ac5a31fb5&page=1")

        }
    }

    @Test
    fun getDataFromMovieID_correctPageSize() {
        runBlocking {
            enqueueMockResponse("moviedetails.json")
            val responseBody =
                service.getDataFromMovieID("508943", "55957fcf3ba81b137f8fc01ac5a31fb5").body()
            val overview = responseBody!!.id
            assertThat(overview).isEqualTo(508943)

        }
    }

    @Test
    fun getPopularMovies_correctContent() {
        runBlocking {
            enqueueMockResponse("movielist.json")
            val responseBody = service.getPopularMovies("55957fcf3ba81b137f8fc01ac5a31fb5").body()
            val articlesList = responseBody!!.movies
            val article = articlesList[0]
            assertThat(article.title).isEqualTo("The Boss Baby: Family Business")
            assertThat(article.posterPath).isEqualTo("/5dExO5G2iaaTxYnLIFKLWofDzyI.jpg")
            assertThat(article.releaseDate).isEqualTo("2021-07-01")


        }
    }


    @After
    fun tearDown() {
        server.shutdown()
    }
}