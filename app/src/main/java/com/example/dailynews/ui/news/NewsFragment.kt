package com.example.dailynews.ui.news

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asFlow
import androidx.lifecycle.asLiveData
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.dailynews.R
import com.example.dailynews.database.ArticleDatabase
import com.example.dailynews.databinding.FragmentNewsBinding
import com.example.dailynews.models.SourcesItem
import com.example.dailynews.models.asDatabaseModel
import com.example.dailynews.repository.ArticleRepository
import com.example.dailynews.utils.observeOnce
import com.google.android.material.tabs.TabLayout
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@ExperimentalCoroutinesApi
class NewsFragment : Fragment() {
    private lateinit var binding: FragmentNewsBinding
    private lateinit var viewModel: NewsViewModel
    private lateinit var source: String
    private lateinit var adapter: NewsAdapter
    private lateinit var categoryId:String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        categoryId = NewsFragmentArgs.fromBundle(requireArguments()).resourceId
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentNewsBinding.inflate(inflater, container, false)
        val database = ArticleDatabase.getInstance(requireContext())
        val repository = ArticleRepository(database)
        val viewModelFactory = NewsViewModel.NewsViewModelFactory(repository)
        adapter = NewsAdapter(NewsAdapter.OnArticleClicked {
            findNavController().navigate(
                NewsFragmentDirections.actionNewsFragmentToArticleFragment(
                    it
                )
            )
        })
        binding.recyclerView.adapter = adapter
        viewModel =
            ViewModelProvider(requireActivity(), viewModelFactory).get(NewsViewModel::class.java)

        lifecycleScope.launchWhenStarted {
            viewModel.sourceId.collect {
                Log.i("id", it!!)
                viewModel.getArticles(it)
            }
        }
        lifecycleScope.launchWhenStarted {
            viewModel.newsList.collect {
                val id = it[0].source?.id!!
                adapter.submitList(it.asDatabaseModel())
                viewModel.setSourceId(id)

            }
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getSources(categoryId)
        viewModel.sources.observe(viewLifecycleOwner) {
            Log.i("lol", it?.size.toString())
            showTabs(it)
        }
    }

    private fun showTabs(sources: List<SourcesItem?>?) {
        sources?.forEach { item ->
            val tab = binding.tablayout.newTab()
            tab.tag = item?.id
            tab.text = item?.name
            binding.tablayout.addTab(tab)
        }
        binding.tablayout.addOnTabSelectedListener(
            object : TabLayout.OnTabSelectedListener {
                override fun onTabSelected(tab: TabLayout.Tab?) {
                    source = tab?.tag as String
                    viewModel.loadData(source)
                    lifecycleScope.launchWhenCreated {

                    }
                }

                override fun onTabUnselected(tab: TabLayout.Tab?) {
                }

                override fun onTabReselected(tab: TabLayout.Tab?) {
                    source = tab?.tag as String
                    viewModel.loadData(source)
                }
            }

        )
        binding.tablayout.getTabAt(0)?.select()

    }
}
