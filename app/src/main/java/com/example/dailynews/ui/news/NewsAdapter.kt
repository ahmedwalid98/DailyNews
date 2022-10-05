package com.example.dailynews.ui.news

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.dailynews.database.Article
import com.example.dailynews.databinding.ItemNewsBinding

class NewsAdapter(private val onArticleClicked: OnArticleClicked): ListAdapter<Article, NewsAdapter.NewsViewHolder>(NewsDiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        return NewsViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val article = getItem(position)
        holder.bind(article)
        holder.binding.container.setOnClickListener {
            onArticleClicked.onItemClicked(article)
        }
    }
    class NewsViewHolder(val binding:ItemNewsBinding):RecyclerView.ViewHolder(binding.root){
        fun bind(article: Article){
            binding.article = article
        }
        companion object{
            fun from(parent: ViewGroup):NewsViewHolder{
                val inflater = LayoutInflater.from(parent.context)
                val view = ItemNewsBinding.inflate(inflater,parent,false)
                return NewsViewHolder(view)
            }
        }
    }

    class NewsDiffCallback:DiffUtil.ItemCallback<Article>() {
        override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem.url == newItem.url
        }

        override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem == newItem
        }
    }

    class OnArticleClicked(val onArticleClicked: (article:Article)->Unit){
        fun onItemClicked(article: Article) = onArticleClicked(article)
    }

}