package samples.droidproject.newsstories.models

import samples.droidproject.newsstories.models.Article


data class NewsResponse (
    val articles: MutableList<Article>,
    val status: String,
    val totalResults: Int
)