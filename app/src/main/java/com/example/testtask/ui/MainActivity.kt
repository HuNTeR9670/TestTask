package com.example.testtask.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.testtask.R
import com.example.testtask.data.model.Cats
import com.example.testtask.ui.adapters.AllCatsAdapter
import com.example.testtask.utils.EndlessScrollListener
import com.example.testtask.vm.CatsViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_featured_cats.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.cat_items.*

class MainActivity : AppCompatActivity(R.layout.activity_main) {

    private val catsViewModel: CatsViewModel by viewModels()
    private val catsAdapter = AllCatsAdapter { cats: Cats, view: View ->
        addFeatureCat(cats = cats, view = view)
    }
    private var layoutManager = GridLayoutManager(this, 2)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        bottom_navigation_featured.selectedItemId = R.id.page_1
        catsListRV.layoutManager = layoutManager
        catsListRV.adapter = catsAdapter

        catsViewModel.getCats(1).observe(this, Observer {
            catsAdapter.refreshCats(it)
        }
        )

        bottom_navigation.setOnNavigationItemSelectedListener(nav)
        catsListRV.addOnScrollListener(scrollListener)
    }

    private fun addFeatureCat(cats: Cats, view: View) {
        view.setOnClickListener {
            downloadBtn.setOnClickListener {
                catsViewModel.downloadImage(cats)
            }
            featureBtn.setOnClickListener {
                Toast.makeText(
                    applicationContext,
                    "Cat add to feature!",
                    Toast.LENGTH_SHORT
                ).show()
                catsViewModel.addFeaturedCats(cats)
            }
        }
    }

    private val scrollListener = object : EndlessScrollListener(layoutManager) {
        override fun onLoadMore(page: Int, totalItemsCount: Int, view: RecyclerView?) {
            Log.e("MainActivity", "End of scroll")
            catsViewModel.getMoreCats(page)
        }
    }

    private val nav = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.page_1 -> {
                true
            }
            R.id.page_2 -> {
                val featuredIntent = Intent(this, FeaturedCatsActivity()::class.java)
                startActivity(featuredIntent)
                true
            }
            else -> false
        }
    }

}