package com.example.testtask.ui

import android.app.DownloadManager
import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.view.get
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.example.testtask.R
import com.example.testtask.data.model.Cats
import com.example.testtask.ui.adapters.AllCatsAdapter
import com.example.testtask.vm.CatsViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_featured_cats.*
import kotlinx.android.synthetic.main.activity_main.*
import java.io.File

class FeaturedCatsActivity : AppCompatActivity(R.layout.activity_featured_cats) {

    private val catsViewModel: CatsViewModel by viewModels()
    private val catsAdapter = AllCatsAdapter { featuredCats: Cats, _: View -> catsViewModel.downloadImage(featuredCats) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        featureBtn.visibility = View.INVISIBLE

        featureCatsRV.layoutManager = GridLayoutManager(this, 2)
        featureCatsRV.adapter = catsAdapter

        bottom_navigation_featured.setOnNavigationItemSelectedListener(nav)
        bottom_navigation_featured.selectedItemId = R.id.page_2
        catsViewModel.getFeaturedCats()?.observe(this, Observer {
          catsAdapter.refreshCats(it)
        }
        )
    }

    private val nav = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.page_1 -> {
                this.finish()
                true
            }
            R.id.page_2 -> {
                true
            }
            else -> false
        }
    }
}