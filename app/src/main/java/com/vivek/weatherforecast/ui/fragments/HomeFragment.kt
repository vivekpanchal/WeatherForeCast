package com.vivek.weatherforecast.ui.fragments

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.vivek.weatherforecast.R
import com.vivek.weatherforecast.data.local.entity.WeatherResponseEntity
import com.vivek.weatherforecast.databinding.HomeFragmentBinding
import com.vivek.weatherforecast.di.Injectable
import com.vivek.weatherforecast.utils.Status
import timber.log.Timber
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject


class HomeFragment : Fragment(), Injectable {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var binding: HomeFragmentBinding
    val PERMISSION_ID = 42


    companion object {
        fun newInstance() =
            HomeFragment()
    }

    private lateinit var viewModel: HomeViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater, R.layout.home_fragment, container, false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())

    }

    @SuppressLint("MissingPermission")
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(HomeViewModel::class.java)
        fetchData()


        binding.refresh.setOnRefreshListener {
            fetchData()
            binding.refresh.isRefreshing = false
        }


    }

    @SuppressLint("MissingPermission")
    private fun fetchData() {
        if (checkPermissions()) {
            if (isLocationEnabled(requireContext())) {
                fusedLocationClient.lastLocation
                    .addOnSuccessListener { location: Location? ->
                        val latitude = location?.latitude
                        val longitude = location?.longitude
                        if (latitude != null && longitude != null) {
                            viewModel.getWeatherResponse(latitude, longitude)
                                .observe(viewLifecycleOwner, Observer { data ->
                                    data?.let { resource ->
                                        when (resource.status) {
                                            Status.SUCCESS -> {
                                                resource.data?.let {
                                                    binding.progressBar.visibility = View.GONE
                                                    Timber.d("weather data " + it.current.clouds)
                                                    updateUI(it)

                                                }
                                            }
                                            Status.ERROR -> {
                                                binding.progressBar.visibility = View.GONE
                                                Timber.d("Error%s", data.message)
                                                Toast.makeText(
                                                    activity,
                                                    data.message,
                                                    Toast.LENGTH_LONG
                                                ).show()
                                            }
                                            Status.LOADING -> {
                                                binding.progressBar.visibility = View.VISIBLE
                                                Timber.d("Loading")
                                            }
                                        }
                                    }

                                })
                        }


                    }
            } else {
                Toast.makeText(activity, "Turn on location", Toast.LENGTH_LONG).show()
                val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                startActivity(intent)
            }
        } else {
            requestPermissions()
        }
    }

    private fun updateUI(res: WeatherResponseEntity) {

        Timber.d("icon name %s", res.current.weather[0].icon)
        Glide.with(this)
            .load("http://openweathermap.org/img/wn/${res.current.weather[0].icon}@2x.png")
            .apply(
                RequestOptions()
                    .placeholder(R.drawable.loading_animation)
                    .error(R.drawable.ic_broken_image)
            )
            .fitCenter()
            .into(binding.imgIcon)

        binding.tvStatus.text = res.current.weather.get(0).main
        binding.tvTemperature.text = "${res.current.temp}${getString(R.string.celsius)}"
        binding.tvSunrise.text =
            SimpleDateFormat("hh:mm a", Locale.ENGLISH).format(Date(res.current.sunrise * 1000))
        binding.tvSunset.text =
            SimpleDateFormat("hh:mm a", Locale.ENGLISH).format(Date(res.current.sunset * 1000))
        binding.tvWind.text = "${res.current.windSpeed} km/h"
        binding.tvHumidity.text = "${res.current.humidity}  %"
        binding.tvPressure.text = "${res.current.humidity}  mb"

    }


    private fun checkPermissions(): Boolean {
        if (ActivityCompat.checkSelfPermission(
                requireActivity(),
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(
                requireActivity(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            return true
        }
        return false
    }

    private fun requestPermissions() {
        ActivityCompat.requestPermissions(
            requireActivity(),
            arrayOf(
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION
            ),
            PERMISSION_ID
        )
    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        if (requestCode == PERMISSION_ID) {
            if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                // Granted. Start getting the location information
            }
        }
    }

    private fun isLocationEnabled(mContext: Context): Boolean {
        val lm = mContext.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return lm.isProviderEnabled(LocationManager.GPS_PROVIDER) || lm.isProviderEnabled(
            LocationManager.NETWORK_PROVIDER
        )
    }


}



