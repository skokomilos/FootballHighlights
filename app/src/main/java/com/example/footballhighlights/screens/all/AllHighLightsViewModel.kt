package com.example.footballhighlights.screens.all

import androidx.lifecycle.ViewModel
import com.example.footballhighlights.data.repository.Repository
import com.example.footballhighlights.internal.lazyDeferred

class AllHighLightsViewModel(private val repository: Repository) : ViewModel() {

    val matches by lazyDeferred {
        repository.getHighLights()
    }
}
