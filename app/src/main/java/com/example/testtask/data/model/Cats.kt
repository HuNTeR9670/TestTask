package com.example.testtask.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "featured_cats")
data class Cats(
    @PrimaryKey
    @SerializedName("id")
    val id: String,
    @ColumnInfo(name = "url")
    @SerializedName("url")
    val url: String,
    @ColumnInfo(name = "height")
    @SerializedName("height")
    val height: Int,
    @ColumnInfo(name = "width")
    @SerializedName("width")
    val width: Int
)
