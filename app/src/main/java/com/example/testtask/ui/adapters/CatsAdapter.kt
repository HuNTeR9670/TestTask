package com.example.testtask.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.testtask.R
import com.example.testtask.data.model.Cats
import kotlinx.android.synthetic.main.cat_items.view.*

class AllCatsAdapter(private val clickListener: (Cats, View) -> Unit) : RecyclerView.Adapter<AllCatsVH>() {

    private var catsList: List<Cats> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AllCatsVH =
        AllCatsVH(LayoutInflater.from(parent.context).inflate(R.layout.cat_items, parent, false))

    override fun getItemCount(): Int {
        return catsList.count()
    }

    override fun onBindViewHolder(holder: AllCatsVH, position: Int): Unit = holder.itemView.run {
        Glide.with(context)
            .load(catsList[position].url)
            .into(catsIV)
        featureBtn.setOnClickListener {
            clickListener(catsList[position], it)
        }
        downloadBtn.setOnClickListener {
            clickListener(catsList[position], it)
        }
    }

    fun refreshCats(cats: List<Cats>) {
        this.catsList = cats
        notifyDataSetChanged()
    }

}

class AllCatsVH(itemView: View) : RecyclerView.ViewHolder(itemView)