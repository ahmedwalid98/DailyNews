package com.example.dailynews.repository

import com.example.dailynews.Constants
import com.example.dailynews.database.ArticleDatabase
import com.example.dailynews.models.asDatabaseModel
import com.example.dailynews.network.RetrofitClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ArticleRepository(private val database: ArticleDatabase) {
     suspend fun articles(id:String) = database.articlesDAO().getArticles(id)


    suspend fun refreshData(id:String?){
        withContext(Dispatchers.IO){

                val articles = RetrofitClient.apiService.getEverything(Constants.API_KEY, id!!)
                database.articlesDAO().insertArticles(
                    articles.articles.asDatabaseModel()
                )
            }
        }
}