package com.example.footballhighlights.data.network

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.footballhighlights.ApiService
import com.example.footballhighlights.data.db.entity.Match
import com.example.footballhighlights.data.network.response.HighLightsResponse
import com.example.footballhighlights.internal.NoConnectivityException
import retrofit2.HttpException

class HighLightsNetworkDataSourceImpl(
    private val api: ApiService
) : HighLightsNetworkDataSource {

    private val _downloadedHighLights = MutableLiveData<List<Match>>()
    override val downloadedHighLights: LiveData<List<Match>>
        get() = _downloadedHighLights

    override suspend fun fetchHightLights() {
        try{
            val fetchHighLights = api.getHighLights()
            _downloadedHighLights.postValue(fetchHighLights)
        }catch (e: NoConnectivityException){
            Log.e("Connectivity", "No internet connection.", e)
        }catch (e: HttpException){
            Log.e("Connectivity", "HTTP 502 Bad Gateway", e)
        }
    }
}