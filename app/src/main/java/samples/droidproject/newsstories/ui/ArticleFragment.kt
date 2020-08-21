package samples.droidproject.newsstories.ui

import android.content.res.Configuration
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import androidx.fragment.app.Fragment
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.navigation.fragment.navArgs
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_article.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import samples.droidproject.newsstories.MainActivity
import samples.droidproject.newsstories.R
import samples.droidproject.newsstories.models.Article
import samples.droidproject.newsstories.utils.toast
import samples.droidproject.newsstories.viewModels.NewsViewModel

class ArticleFragment : Fragment(R.layout.fragment_article) {

    private var article: Article? = null
    lateinit var viewModel: NewsViewModel
    lateinit var webView: WebView
    lateinit var fab: FloatingActionButton

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v = super.onCreateView(inflater, container, savedInstanceState)

        webView = v?.findViewById(R.id.webView) as WebView
        fab = v.findViewById(R.id.fab)
        arguments?.let {
            article = ArticleFragmentArgs.fromBundle(it).article

        }
        viewModel = (activity as MainActivity).viewModel
        Log.d("ArticleFragment", article!!.url)



        webView.apply {
           webViewClient = WebViewClient()
            loadUrl(article!!.url)
            settings.javaScriptEnabled
            canGoBack()
            goBack()
        }

        fab.setOnClickListener {

            viewModel.saveArticle(article!!)
            view?.let { it1 -> Snackbar.make(it1, "Article saved successfully", Snackbar.LENGTH_SHORT).show() }
        }
        return v
    }


}

