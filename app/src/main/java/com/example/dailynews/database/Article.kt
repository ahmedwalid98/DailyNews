package com.example.dailynews.database


import android.os.Parcelable
import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "article_table_name")
@Parcelize
data class Article(
    val publishedAt: String?,
    val author: String?,
    @PrimaryKey
    val url: String,
    val description:String?,
    val title: String?,
    val urlToImage:String?,
    val id:String?
):Parcelable
