package com.bd.blacksky.ui.activities

import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.location.Geocoder
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Looper
import android.view.Window
import android.view.WindowManager
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.bd.blacksky.R
import com.bd.blacksky.databinding.ActivityLiveBinding
import com.bd.blacksky.ui.fragment.LiveFragment
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance
import java.util.*

class LiveActivity : AppCompatActivity() {

    private val liveFragment = LiveFragment()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        val binding : ActivityLiveBinding = DataBindingUtil.setContentView(this,R.layout.activity_live)



        /* Changin the action bar color to mach the full background */
        val window: Window = this.window
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = ContextCompat.getColor(applicationContext, R.color.main_bg)
        window.navigationBarColor = ContextCompat.getColor(applicationContext, R.color.main_bg)

        replaceFragment(liveFragment)

    }



    fun AppCompatActivity.replaceFragment(fragment: Fragment){
        val fragmentManager = supportFragmentManager
        val transaction = fragmentManager.beginTransaction()
        transaction.replace(R.id.fragment_container_activity_live,fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }

}