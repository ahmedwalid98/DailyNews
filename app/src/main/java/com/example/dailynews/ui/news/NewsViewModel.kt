package com.example.dailynews.ui.news

import android.app.Application
import androidx.lifecycle.*
import com.example.dailynews.Constants
import com.example.dailynews.Constants.Companion.API_KEY
import com.example.dailynews.database.Article
import com.example.dailynews.database.ArticleDatabase
import com.example.dailynews.models.ArticlesItem
import com.example.dailynews.models.SourcesItem
import com.example.dailynews.models.asDatabaseModel
import com.example.dailynews.network.RetrofitClient
import com.example.dailynews.repository.ArticleRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.lang.Exception


@ExperimentalCoroutinesApi
class NewsViewModel(private val myRepository: ArticleRepository) : ViewModel() {
    private val _newsList = MutableSharedFlow<List<ArticlesItem>>()
    val newsList = _newsList.asSharedFlow()
    private val _articles = MutableSharedFlow<List<Article>>()
    val articles = _articles.asSharedFlow()
    val searchedNewsList =MutableLiveData<List<ArticlesItem?>?>()

    private val _sources = MutableLiveData<List<SourcesItem?>?>()
    val sources: LiveData<List<SourcesItem?>?>
        get() = _sources
    private val _sourceId = MutableSharedFlow<String?>()
    val sourceId = _sourceId.asSharedFlow()

    fun setSourceId(id: String) {
        viewModelScope.launch {
            _sourceId.emit(id)
        }
    }

    fun removeSource() {
        _sourceId.resetReplayCache()
    }

    fun getArticles(id: String) {
        viewModelScope.launch {
            val articleList = myRepository.articles(id)
            _articles.emit(articleList)
        }
    }

    fun loadData(id: String) {
        viewModelScope.launch {
            val articles = RetrofitClient.apiService.getEverything(API_KEY, id)
            _newsList.emit(articles.articles)
            myRepository.refreshData(id)
        }
    }

    fun mockData() {
        viewModelScope.launch {
            _articles.emit(emptyList())
        }
    }

    fun getSources(category: String) {
        viewModelScope.launch {
            try {
                val response = RetrofitClient.apiService.getSources(Constants.API_KEY, category)
                _sources.postValue(response.sources)
            } catch (e: Exception) {
                throw Exception("network error")
            }
        }
    }

    class NewsViewModelFactory(private val myRepository: ArticleRepository) :
        ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(NewsViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return NewsViewModel(myRepository) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }
    fun searchByKeywordAndSource(keyWord:String , sourcesItem: SourcesItem){

        viewModelScope.launch {
            try {
                val result=  RetrofitClient.apiService.searchInNews(Constants.API_KEY,keyWord,sourcesItem.id?:"")

                searchedNewsList.value = result.articles

            }catch (e:Exception){

            }
        }

    }
}