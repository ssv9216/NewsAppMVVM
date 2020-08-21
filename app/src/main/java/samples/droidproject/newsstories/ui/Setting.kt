package samples.droidproject.newsstories.ui

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toolbar
import androidx.appcompat.app.AppCompatDelegate
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_setting.*
import samples.droidproject.newsstories.MainActivity
import samples.droidproject.newsstories.R

class Setting : Fragment(R.layout.fragment_setting) {

    lateinit var prefs: SharedPreferences
    lateinit var toolBar:androidx.appcompat.widget.Toolbar

    override fun onActivityCreated(savedInstanceState: Bundle?) {
            super.onActivityCreated(savedInstanceState)

            toolBar = (activity as MainActivity).maToolBar

            toolBar.title = "Setting"

            prefs = requireContext().getSharedPreferences("button", Context.MODE_PRIVATE)

            val enabled = prefs.getBoolean("button",true)


            if (enabled){
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            }else{
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }

            val isEnable = prefs.getBoolean("button",true)
            if (isEnable){
                darkModeSwitch.isChecked = true
            }

            darkModeSwitch.setOnCheckedChangeListener{_,isChecked->

                if (isChecked){
                    prefs.edit().putBoolean("button",true).apply()
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                    activity?.finish()
                    startActivity(Intent(context,MainActivity::class.java))
                    findNavController().navigate(R.id.setting)
                }else{
                    prefs.edit().putBoolean("button",false).apply()
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                    activity?.finish()
                    startActivity(Intent(context,MainActivity::class.java))
                    findNavController().navigate(R.id.setting)
                }

            }

    }

}