package samples.droidproject.newsstories

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.PersistableBundle
import android.util.Log
import android.view.View
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.ContentProviderCompat.requireContext
import kotlinx.android.synthetic.main.activity_splash_screen.*
import samples.droidproject.newsstories.utils.toast

class SplashScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
    }

    private lateinit var prefs: SharedPreferences

    override fun onStart() {
        super.onStart()

        prefs = this.getSharedPreferences("button", Context.MODE_PRIVATE)

        val enabled = prefs.getBoolean("button",false)

        if (enabled){
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        }else{
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }


        checkInternetConnection.visibility = View.INVISIBLE
        if (verifyAvailableNetwork(activity = this)){
            startMainActivity()
        }else{
            checkInternetConnection.visibility = View.VISIBLE
        }
    }

    private fun startMainActivity() {
        Handler().postDelayed(
            {
                startActivity(Intent(this, MainActivity::class.java))
                finish()

            }
            , 2000
        )
    }



    fun verifyAvailableNetwork(activity:AppCompatActivity):Boolean{
        val connectivityManager=activity.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo=connectivityManager.activeNetworkInfo
        return  networkInfo!=null && networkInfo.isConnected
    }


}