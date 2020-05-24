package com.example.footballhighlights.data.network

import androidx.lifecycle.LiveData
import com.example.footballhighlights.data.db.entity.Match
import com.example.footballhighlights.data.network.response.HighLightsResponse

interface HighLightsNetworkDataSource {
    val downloadedHighLights:  LiveData<List<Match>>

    suspend fun fetchHightLights()
}