package com.example.dailynews.ui.article

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.dailynews.R
import com.example.dailynews.databinding.FragmentArticleBinding


class ArticleFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding = FragmentArticleBinding.inflate(inflater, container, false)
        val article = ArticleFragmentArgs.fromBundle(requireArguments()).article
        binding.article = article
        return binding.root
    }

}