package com.example.footballhighlights.screens.all

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import androidx.activity.OnBackPressedCallback
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager

import com.example.footballhighlights.R
import com.example.footballhighlights.data.db.entity.Match
import com.example.footballhighlights.screens.ScopedFragment
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import kotlinx.android.synthetic.main.all_high_lights_fragment.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance

class AllHighLightsFragment : ScopedFragment(), KodeinAware {

    override val kodein: Kodein by closestKodein()

    companion object {
        fun newInstance() = AllHighLightsFragment()
    }

    private lateinit var viewModel: AllHighLightsViewModel
    private val factory : ViewModelFactory by instance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activity?.onBackPressedDispatcher?.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
               if(webview.isVisible){
                   webview.removeAllViews()
                   webview.visibility = View.GONE
               }else if(!webview.isVisible){
                    activity!!.finish()
               }
            }
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.all_high_lights_fragment, container, false)
    }

    override fun bindUI () = launch {
        viewModel.matches.await().observe(viewLifecycleOwner, Observer {
            if(it != null){
                group_loading_favorites.visibility = View.GONE
                initRecyclerView(it.toHLItem())
            }
        })
    }


    @SuppressLint("SetJavaScriptEnabled")
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel = ViewModelProvider(this, factory)
            .get(AllHighLightsViewModel::class.java)

        bindUI()
    }


    private fun List<Match>.toHLItem(): List<HighLightItem>{
        return this.map {
            HighLightItem(it)
        }
    }

    private fun initRecyclerView(items: List<HighLightItem>){
        val groupAdapter = GroupAdapter<GroupieViewHolder>().apply {
            addAll(items)
        }

        recyclerViewHL.apply {
            layoutManager = LinearLayoutManager(this@AllHighLightsFragment.context)
            adapter = groupAdapter
        }

        groupAdapter.setOnItemClickListener{ item, view ->
            if(item is HighLightItem){

                webview.visibility = View.VISIBLE
                webview.bringToFront()
                webview!!.settings.javaScriptEnabled = true
                webview!!.settings.loadWithOverviewMode = true
                webview!!.settings.useWideViewPort = true
                webview!!.loadData(item.match.embed,"text/html", "UTF-8")

            }

        }
    }
}
