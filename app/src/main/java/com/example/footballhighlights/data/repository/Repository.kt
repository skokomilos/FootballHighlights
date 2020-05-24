package com.example.footballhighlights.data.repository

import androidx.lifecycle.LiveData
import com.example.footballhighlights.data.db.entity.Match

interface Repository {

    suspend fun getHighLights():  LiveData<List<Match>>
    suspend fun fetchHightLights()
}