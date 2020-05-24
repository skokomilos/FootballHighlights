package com.example.footballhighlights.data.network.response

import com.example.footballhighlights.data.db.entity.Match

data class HighLightsResponse(
    val highLights: List<Match>
) {
}