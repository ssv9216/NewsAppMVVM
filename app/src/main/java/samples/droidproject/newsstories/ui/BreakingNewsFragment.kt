package samples.droidproject.newsstories.ui

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.AbsListView
import android.widget.ImageButton
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.load.engine.Resource
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_breaking_news.*
import samples.droidproject.newsstories.MainActivity
import samples.droidproject.newsstories.models.Article
import samples.droidproject.newsstories.R
import samples.droidproject.newsstories.adapters.NewsAdapter
import samples.droidproject.newsstories.models.NewsResponse
import samples.droidproject.newsstories.utils.Constants.Companion.QUERY_PAGE_SIZE
import samples.droidproject.newsstories.viewModels.NewsViewModel
import java.util.*

class BreakingNewsFragment :Fragment(R.layout.fragment_breaking_news) {

    lateinit var viewModel: NewsViewModel
    lateinit var newsAdapter: NewsAdapter
    lateinit var toolBar:androidx.appcompat.widget.Toolbar
    lateinit var setting: ImageButton

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = (activity as MainActivity).viewModel
        setupRecyclerView()
        toolBar = (activity as MainActivity).maToolBar
        setting =(activity as MainActivity).maSetting
        toolBar.title = "News"
        setting.visibility = View.VISIBLE

        viewModel.breakingNews.observe(viewLifecycleOwner, Observer { response->
            when(response){
                is samples.droidproject.newsstories.utils.Resource.Success ->{
                    hideProgressBar()
                    response.data?.let {newsResponse->
                        newsAdapter.differ.submitList(newsResponse.articles.toList())
                        val totalPages = newsResponse.totalResults / QUERY_PAGE_SIZE + 2
                        isLastPage = viewModel.breakingNewsPage == totalPages
                        if(isLastPage){
                            fbnRecyclerView.setPadding(0,0,0,0)
                        }
                    }
                }

                is samples.droidproject.newsstories.utils.Resource.Error ->{
                    response.message?.let {
                        Log.e("TAG","An error occured :$it")
                    }
                }

                is samples.droidproject.newsstories.utils.Resource.Loading->{
                    showProgressBar()
                }
            }
        })
    }

    var isLoading = false
    var isLastPage = false
    var isScrolling = false

    val scrollListener = object : RecyclerView.OnScrollListener(){
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)

            val layoutManager = recyclerView.layoutManager as LinearLayoutManager
            val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()
            val visibleItemCount = layoutManager.childCount
            val totalItemCount = layoutManager.itemCount

            val isNotLoadingAndNotLastPage = !isLoading && !isLastPage
            val isAtLastItem = firstVisibleItemPosition + visibleItemCount >= totalItemCount
            val isNotAtBeginning = firstVisibleItemPosition >= 0
            val isTotalMoreThanVisible = totalItemCount >= QUERY_PAGE_SIZE
            val shouldPaginate = isNotLoadingAndNotLastPage && isAtLastItem && isNotAtBeginning && isTotalMoreThanVisible && isScrolling
            if (shouldPaginate){
                viewModel.getBreakingNews("in")
                isScrolling = false
            }
//            else{
//                fbnRecyclerView.setPadding(0,0,0,0)
//
//            }

        }

        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            super.onScrollStateChanged(recyclerView, newState)
            if(newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL){
                isScrolling = true
            }
        }
    }

    fun hideProgressBar(){
        isLoading = false
        paginationProgressBar.visibility = View.INVISIBLE
    }

    fun showProgressBar(){
        isLoading = true
        paginationProgressBar.visibility = View.VISIBLE
    }

    private fun setupRecyclerView() {
        newsAdapter = NewsAdapter()
        fbnRecyclerView.apply {
            adapter = newsAdapter
            layoutManager = LinearLayoutManager(activity)
            addOnScrollListener(this@BreakingNewsFragment.scrollListener)
        }
    }
}