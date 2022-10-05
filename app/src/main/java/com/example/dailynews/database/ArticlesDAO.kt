package com.example.dailynews.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.Deferred

@Dao
interface ArticlesDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertArticles(article: List<Article>)

    @Query("SELECT * FROM article_table_name WHERE id = :id order by publishedAt desc")
    suspend fun getArticles(id:String): List<Article>
}