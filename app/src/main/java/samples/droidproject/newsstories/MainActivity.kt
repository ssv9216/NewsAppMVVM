package samples.droidproject.newsstories

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toolbar
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import kotlinx.android.synthetic.main.activity_main.*
import samples.droidproject.newsstories.db.NewsRoomDatabase
import samples.droidproject.newsstories.repository.NewsRepository
import samples.droidproject.newsstories.viewModels.NewsViewModel
import samples.droidproject.newsstories.viewModels.NewsViewModelProviderFactory

class MainActivity : AppCompatActivity() {

    private val TAG = "MainActivity"
    lateinit var toolbar: Toolbar
    companion object {
        const val BASE_URL = "https://cat-fact.herokuapp.com"
    }
    lateinit var viewModel:NewsViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        maSetting.visibility = View.VISIBLE
        maToolBar.title ="News"

        val newsRepository = NewsRepository(NewsRoomDatabase(this))
        val viewModelProviderFactory = NewsViewModelProviderFactory(newsRepository)
        viewModel = ViewModelProvider(this,viewModelProviderFactory).get(NewsViewModel::class.java)

        val navController = Navigation.findNavController(this,
            R.id.fragment
        )
        bottomNavigationView.setupWithNavController(navController)

        maSetting.setOnClickListener {
            navController.navigate(R.id.setting)
            maSetting.visibility = View.GONE
        }



    }


}