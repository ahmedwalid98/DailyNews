package com.example.dailynews.ui.categories

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.dailynews.R
import com.example.dailynews.databinding.FragmentCategoryBinding

class CategoryFragment : Fragment() {
    val categories = listOf(
        Category(
            "sports", R.drawable.sports,
            R.string.sports, R.color.red
        ),
        Category(
            "technology", R.drawable.politics,
            R.string.technology, R.color.blue
        ),
        Category(
            "health", R.drawable.health,
            R.string.health, R.color.pink
        ),
        Category(
            "business", R.drawable.bussines,
            R.string.business, R.color.brown_orange
        ),
        Category(
            "general", R.drawable.environment,
            R.string.general, R.color.baby_blue
        ),
        Category(
            "science", R.drawable.science,
            R.string.science, R.color.yellow
        ),
    )
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        val binding = FragmentCategoryBinding.inflate(inflater,container,false)
        val adapter = CategoryAdapter(categories, CategoryAdapter.OnCategoryClicked {
            findNavController().navigate(CategoryFragmentDirections.actionCategoryFragmentToNewsFragment(it.id))
        })
        binding.recyclerView.adapter = adapter
        return binding.root
    }

}