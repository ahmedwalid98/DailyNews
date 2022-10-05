package com.example.dailynews.models

import com.example.dailynews.database.Article
import com.google.gson.annotations.SerializedName

data class NewsResponse(
    @field:SerializedName("totalResults")
    val totalResults: Int,

    @field:SerializedName("articles")
    val articles: List<ArticlesItem>,
):BaseResponse()
data class ArticlesItem(

    @field:SerializedName("publishedAt")
    val publishedAt: String?,

    @field:SerializedName("author")
    val author: String?,

    @field:SerializedName("urlToImage")
    val urlToImage: String?,

    @field:SerializedName("description")
    val description: String?,

    @field:SerializedName("source")
    val source: SourcesItem?,

    @field:SerializedName("title")
    val title: String?,

    @field:SerializedName("url")
    val url: String?,

    @field:SerializedName("content")
    val content: String?
)

fun List<ArticlesItem>.asDatabaseModel(): List<Article>{
    return map {
           Article(
               url = it.url!!,
               description = it.content,
               title = it.title,
               publishedAt = it.publishedAt,
               author = it.author,
               urlToImage = it.urlToImage,
               id = it.source!!.id!!
           )


    }
}

