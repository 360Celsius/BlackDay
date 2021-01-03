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
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.bd.blacksky.R
import com.bd.blacksky.databinding.ActivityLiveBinding
import com.bd.blacksky.ui.fragment.LiveFragment
import com.bd.blacksky.utils.LocationPermission
import com.bd.blacksky.viewmodels.LiveActivityToLiveFragmentSharedViewModel
import com.bd.blacksky.viewmodels.factories.LiveActivityToLiveFragmentSharedViewModelFactory
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance
import java.util.*

class LiveActivity : AppCompatActivity(), KodeinAware {

    private val liveFragment = LiveFragment()

    //Dependancy injection
    override val kodein by kodein()
    private val liveActivityToLiveFragmentSharedViewModelFactory: LiveActivityToLiveFragmentSharedViewModelFactory by instance()


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


    @SuppressLint("MissingPermission")
    private fun setUpLocationListener() {
        val fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
        // for getting the current location update after every 2 seconds with high accuracy
        val locationRequest = LocationRequest().setInterval(2000).setFastestInterval(2000).setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
        fusedLocationProviderClient.requestLocationUpdates(
            locationRequest,
            object : LocationCallback() {
                override fun onLocationResult(locationResult: LocationResult) {
                    super.onLocationResult(locationResult)
                    for (location in locationResult.locations) {

                        val geocoder = Geocoder(applicationContext, Locale.getDefault())
                        val addresses: List<*> = geocoder.getFromLocation(location.latitude, location.longitude, 1)


                    }
                    // Few more things we can do here:
                    // For example: Update the location of user on server
                }
            },
            Looper.myLooper()
        )
    }

    override fun onStart() {
        super.onStart()
        when {
            LocationPermission.isAccessFineLocationGranted(this) -> {
                when {
                    LocationPermission.isLocationEnabled(this) -> {
                        setUpLocationListener()
                    }
                    else -> {
                        LocationPermission.showGPSNotEnabledDialog(this)
                    }
                }
            }
            else -> {
                LocationPermission.requestAccessFineLocationPermission(
                    this,
                    LOCATION_PERMISSION_REQUEST_CODE
                )
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        val liveActivityToLiveFragmentSharedViewModel = ViewModelProviders.of(this,liveActivityToLiveFragmentSharedViewModelFactory).get(LiveActivityToLiveFragmentSharedViewModel::class.java)


        when (requestCode) {
            LOCATION_PERMISSION_REQUEST_CODE -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    when {
                        LocationPermission.isLocationEnabled(this) -> {
                            setUpLocationListener()
                            liveActivityToLiveFragmentSharedViewModel?.isLocationPermissionsApproved?.value = true
                        }
                        else -> {
                            LocationPermission.showGPSNotEnabledDialog(this)
                        }
                    }
                } else {
                    Toast.makeText(
                        this,
                        getString(R.string.location_permission_not_granted),
                        Toast.LENGTH_LONG
                    ).show()
                    liveActivityToLiveFragmentSharedViewModel?.isLocationPermissionsApproved?.value = false
                }
            }
        }
    }

    companion object {
        private const val LOCATION_PERMISSION_REQUEST_CODE = 999
    }
}