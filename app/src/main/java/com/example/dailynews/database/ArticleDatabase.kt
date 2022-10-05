package com.example.dailynews.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(entities = [Article::class], version = 1, exportSchema = false)
abstract class ArticleDatabase : RoomDatabase() {
    abstract fun articlesDAO(): ArticlesDAO

    companion object {
        @Volatile
        private var INSTANCE: ArticleDatabase? = null
        fun getInstance(context: Context): ArticleDatabase =
            INSTANCE ?: synchronized(ArticleDatabase::class.java) {
                INSTANCE ?: Room.databaseBuilder(context, ArticleDatabase::class.java, "articles")
                    .allowMainThreadQueries()
                    .build().also { INSTANCE = it }
            }
    }
}