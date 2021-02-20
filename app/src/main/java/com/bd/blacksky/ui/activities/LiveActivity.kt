package com.bd.blacksky.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Window
import android.view.WindowManager
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.bd.blacksky.R
import com.bd.blacksky.databinding.ActivityLiveBinding
import com.bd.blacksky.ui.fragment.LiveFragment
import com.bd.blacksky.ui.fragment.SplashFragment
import com.bd.blacksky.viewmodels.SharedViewModel
import com.bd.blacksky.viewmodels.factories.SharedViewModelFactory
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance

class LiveActivity : AppCompatActivity(), KodeinAware {

    private val liveFragment = LiveFragment()
    private val splashFragment = SplashFragment()


    //Dependancy injection
    override val kodein by kodein()

    private val sharedViewModelFactory: SharedViewModelFactory by instance()
    val sharedViewModel: SharedViewModel by lazy {
        ViewModelProvider(this, sharedViewModelFactory).get(SharedViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        val binding : ActivityLiveBinding = DataBindingUtil.setContentView(this,R.layout.activity_live)



        /* Changin the action bar color to mach the full background */
        val window: Window = this.window
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = ContextCompat.getColor(applicationContext, R.color.main_bg)
        window.navigationBarColor = ContextCompat.getColor(applicationContext, R.color.main_bg)

        replaceFragment(splashFragment)

        sharedViewModel.data.observe(this, Observer { isSplashFinished ->
            if(isSplashFinished){
                replaceFragment(liveFragment)
            }
        })

    }



    fun AppCompatActivity.replaceFragment(fragment: Fragment){
        val fragmentManager = supportFragmentManager
        val transaction = fragmentManager.beginTransaction()
        transaction.
        transaction.attach(fragment)
        transaction.replace(R.id.fragment_container_activity_live,fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }
}