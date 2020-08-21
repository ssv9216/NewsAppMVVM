package samples.droidproject.newsstories.viewModels

import android.app.DownloadManager
import android.content.res.Resources
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.handleCoroutineException
import kotlinx.coroutines.launch
import retrofit2.Response
import samples.droidproject.newsstories.R
import samples.droidproject.newsstories.models.Article
import samples.droidproject.newsstories.models.NewsResponse
import samples.droidproject.newsstories.repository.NewsRepository
import samples.droidproject.newsstories.utils.Resource

class NewsViewModel(
    val newsRepository: NewsRepository
):ViewModel(){
    val breakingNews:MutableLiveData<Resource<NewsResponse>> = MutableLiveData()
    var breakingNewsPage = 1
    var breakingnNewsResponse: NewsResponse? = null

    val searchNews:MutableLiveData<Resource<NewsResponse>> = MutableLiveData()
    var searchNewsPage =1
    var searchNewsResponse: NewsResponse?= null

    init {
        getBreakingNews("in")
    }

    fun getBreakingNews(countryCode:String) = viewModelScope.launch {
        breakingNews.postValue(Resource.Loading())
        val response = newsRepository.getBreakingNews(countryCode,breakingNewsPage)
        breakingNews.postValue(handleBreakingNewsResponse(response))
    }

    fun searchNews(searchQuery: String) = viewModelScope.launch {
        searchNews.postValue(Resource.Loading())
        val response = newsRepository.searchNews(searchQuery,searchNewsPage)
        searchNews.postValue(handleSearchNewsResponse(response))
    }

    private fun handleBreakingNewsResponse(response: Response<NewsResponse>): Resource<NewsResponse>? {
        if (response.isSuccessful){
            response.body()?.let { resultResponse->
                breakingNewsPage++
                if (breakingnNewsResponse == null){
                    breakingnNewsResponse = resultResponse
                }else{
                    var oldArticles = breakingnNewsResponse?.articles
                    var newArticles = resultResponse.articles
                    oldArticles?.addAll(newArticles)
                }
                return Resource.Success(breakingnNewsResponse?:resultResponse)
            }
        }
        return Resource.Error(response.message())
    }

    private fun handleSearchNewsResponse(response: Response<NewsResponse>): Resource<NewsResponse>? {
        if (response.isSuccessful){
            response.body()?.let { resultResponse->
                searchNewsPage++
                if (searchNewsPage == null){
                    searchNewsResponse = resultResponse
                }else{
                    var oldArticles = searchNewsResponse?.articles
                    var newArticles = resultResponse.articles
                    oldArticles?.addAll(newArticles)
                }
                return Resource.Success(searchNewsResponse?:resultResponse)
            }
        }
        return Resource.Error(response.message())
    }

    fun saveArticle(article: Article) = viewModelScope.launch {
        newsRepository.insertArticle(article)
    }

    fun getSaveNews() = newsRepository.getSavedNews()

    fun deleteArticle(article: Article) = viewModelScope.launch {
        newsRepository.deleteArticle(article)
    }

}