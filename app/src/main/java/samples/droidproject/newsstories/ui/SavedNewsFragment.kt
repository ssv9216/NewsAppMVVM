package samples.droidproject.newsstories.ui

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_saved_news.*
import samples.droidproject.newsstories.MainActivity
import samples.droidproject.newsstories.models.Article
import samples.droidproject.newsstories.R
import samples.droidproject.newsstories.adapters.NewsAdapter
import samples.droidproject.newsstories.viewModels.NewsViewModel


class SavedNewsFragment : Fragment(R.layout.fragment_saved_news) {

    lateinit var viewModel: NewsViewModel
    lateinit var newsAdapter:NewsAdapter
    lateinit var toolBar:androidx.appcompat.widget.Toolbar

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as MainActivity).viewModel
        setupRecyclerView()

        toolBar = (activity as MainActivity).maToolBar
        toolBar.title = "Favourite"

        val itemTouchHelperCallback = object : ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.UP or  ItemTouchHelper.DOWN,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ){
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                 val article = newsAdapter.differ.currentList[position]
                viewModel.deleteArticle(article)
                Snackbar.make(view,"Deleted",Snackbar.LENGTH_LONG).apply {
                    setAction("UNDO"){
                        viewModel.saveArticle(article)
                }
                    show()
                }
            }

        }
        ItemTouchHelper(itemTouchHelperCallback).apply {
            attachToRecyclerView(fsnRecyclerView)
        }

        viewModel.getSaveNews().observe(viewLifecycleOwner, Observer{articles->
            newsAdapter.differ.submitList(articles)
        })
    }

    private fun setupRecyclerView(){
        newsAdapter = NewsAdapter()
        fsnRecyclerView.apply {
            adapter = newsAdapter
            layoutManager = LinearLayoutManager(activity)
        }
    }

}