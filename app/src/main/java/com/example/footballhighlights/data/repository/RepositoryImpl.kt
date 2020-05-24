package com.example.footballhighlights.data.repository

import androidx.lifecycle.LiveData
import com.example.footballhighlights.data.db.entity.Match
import com.example.footballhighlights.data.network.HighLightsNetworkDataSource

class RepositoryImpl(private val networkDataSource: HighLightsNetworkDataSource) : Repository {


    override suspend fun getHighLights():  LiveData<List<Match>>{
        fetchHightLights()
        return networkDataSource.downloadedHighLights
    }

    override suspend fun fetchHightLights() {
        networkDataSource.fetchHightLights()
    }


}