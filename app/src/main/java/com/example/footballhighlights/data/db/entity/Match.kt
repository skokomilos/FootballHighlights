package com.example.footballhighlights.data.db.entity

import com.google.gson.annotations.SerializedName


data class Match(
    @SerializedName("competition")
    val league: League,
    val date: String,
    val embed: String,
    @SerializedName("side1")
    val home: Home,
    @SerializedName("side2")
    val away: Away,
    val thumbnail: String,
    val title: String,
    val url: String,
    val videos: List<Video>
)