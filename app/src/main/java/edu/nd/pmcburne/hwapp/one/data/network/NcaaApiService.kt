package edu.nd.pmcburne.hwapp.one.data.network

import edu.nd.pmcburne.hwapp.one.data.model.ApiResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface NcaaApiService {

    @GET("scoreboard/basketball-{gender}/d1/{year}/{month}/{day}")
    suspend fun getScores(
        @Path("gender") gender: String,
        @Path("year") year: String,
        @Path("month") month: String,
        @Path("day") day: String
    ): ApiResponse
}

object RetrofitInstance {
    private const val BASE_URL = "https://ncaa-api.henrygd.me/"

    val api: NcaaApiService by lazy {
        retrofit2.Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(retrofit2.converter.gson.GsonConverterFactory.create())
            .build()
            .create(NcaaApiService::class.java)
    }
}