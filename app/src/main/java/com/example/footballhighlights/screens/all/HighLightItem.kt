package com.example.footballhighlights.screens.all

import android.util.Log
import com.example.footballhighlights.R
import com.example.footballhighlights.data.db.entity.Match
import com.example.footballhighlights.internal.glide.GlideApp
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import com.xwray.groupie.kotlinandroidextensions.Item
import kotlinx.android.synthetic.main.item_high_light.*

class HighLightItem(
    val match: Match
): Item(){

    override fun bind(viewHolder: GroupieViewHolder, position: Int) {
        viewHolder.apply {
            league.text = match.league.name
            if(   match.title.contains("-")){
                val modifikovanTxtNoviRedPosleCrte = match.title.replace(" - ","\n", true)
                game_title.text = modifikovanTxtNoviRedPosleCrte
            }
            updateImage()
        }
    }

    override fun getLayout() = R.layout.item_high_light

    private fun GroupieViewHolder.updateImage(){
        GlideApp.with(this.containerView)
            .load(match.thumbnail)
            .into(thumbnail)
    }
}