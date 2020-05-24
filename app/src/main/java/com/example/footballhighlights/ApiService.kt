package com.example.footballhighlights

import com.example.footballhighlights.data.db.entity.Match
import com.example.footballhighlights.data.network.ConnectivityInterceptor
import com.google.gson.GsonBuilder
import kotlinx.coroutines.Deferred
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

const val API_KEY = "c4ddd1d3eamshc8720a2a98e7656p14bfbejsn894ed618659c"

interface ApiService {

    //final url is same as my base url
    @GET(".")
    suspend fun getHighLights(): List<Match>

    companion object {

        //invoke is method that can have any other name but with "invoke" we can can call this method as easy just to write class name in this case CoctailApiService() like instatiation class. We can can it CoctailApiService.invoke() but
        //but first way is much nicier sintatically
        operator fun invoke(
            connectivityInterceptor: ConnectivityInterceptor
        ): ApiService {
            //when we have some value that is pass in query string every time when is called (something like API_KEY) so better way to do this is requestInterceptor
            val requestInterceptor = Interceptor { chain ->

                val url = chain.request()
                    .url()
                    .newBuilder()
                    .build()

                val request: Request = Request.Builder()
                    .url(url)
                    .get()
                    .addHeader("x-rapidapi-host", "free-football-soccer-videos.p.rapidapi.com")
                    .addHeader("x-rapidapi-key", API_KEY)
                    .build()

                return@Interceptor chain.proceed(request)
            }

            val okHttpClient = OkHttpClient.Builder()
                .addInterceptor(requestInterceptor)
                .addInterceptor(connectivityInterceptor)
                .build()

            return Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl("https://free-football-soccer-videos.p.rapidapi.com/")
                .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
                .build()
                .create(ApiService::class.java)
        }
    }
}