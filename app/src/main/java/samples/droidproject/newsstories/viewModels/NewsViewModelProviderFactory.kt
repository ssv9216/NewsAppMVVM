package samples.droidproject.newsstories.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import samples.droidproject.newsstories.repository.NewsRepository

class NewsViewModelProviderFactory (
    val newsRepository: NewsRepository
):ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return NewsViewModel(newsRepository) as T
    }
}
