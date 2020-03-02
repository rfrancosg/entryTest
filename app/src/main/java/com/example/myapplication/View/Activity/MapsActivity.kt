package com.example.myapplication.View.Activity

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.squareup.picasso.Picasso
import kotlin.random.Random
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.util.Log
import com.example.myapplication.R
import com.google.android.gms.maps.model.BitmapDescriptorFactory


private const val PERMISION_REQUEST = 10

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    lateinit var locationManager: LocationManager
    private var hasGps = false
    private var hasNetwork = false
    private lateinit var mMap: GoogleMap
    private var execute = false
    private var permissions = arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION,
        android.Manifest.permission.ACCESS_COARSE_LOCATION)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
        requestPermissions(permissions, PERMISION_REQUEST)
        getDeviceLocation()
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
    }

    @SuppressLint("MissingPermission")
    private fun getDeviceLocation(){
        locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        hasGps = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
        hasNetwork = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
        if (hasGps || hasNetwork){
            if (hasGps){
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 0F, object : LocationListener{
                    override fun onLocationChanged(location: Location?) {
                        if (location != null ){
                            if (!execute){
                                val currentLocation = LatLng(location.latitude, location.longitude)
                                val zoomLvl = 18.0f
                                mMap.addMarker(MarkerOptions().position(currentLocation).title("Marker in Sydney"))
                                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLocation, zoomLvl))
                                generateUsersRandomMarkers(currentLocation)
                            }
                        }
                    }

                    override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {
                        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                    }

                    override fun onProviderEnabled(provider: String?) {
                        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                    }

                    override fun onProviderDisabled(provider: String?) {
                        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                    }

                })
            }

        }
        else{
            startActivity(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS))
        }
    }

    private fun generateUsersRandomMarkers(latLng: LatLng){
        val multiplierFactor = 0.0001
        var randomLatIndex =  Random.nextInt(0,9)
        var randomLonIndex =  Random.nextInt(0,9)
        var randomOperation = Random.nextInt(0,1)
        var latLngList = LatLng(latLng.latitude+randomLatIndex*multiplierFactor,latLng.longitude+randomLonIndex*multiplierFactor)
        var users = intent.getStringArrayListExtra("Users")

        for (item in users){
            randomLatIndex =  Random.nextInt(0,9)
            randomLonIndex =  Random.nextInt(0,9)
            randomOperation = Random.nextInt(0,2)
            if (randomOperation==0){
                latLngList = LatLng(latLng.latitude+randomLatIndex*multiplierFactor,latLng.longitude+randomLonIndex*multiplierFactor)
            }
            else{
                latLngList = LatLng(latLng.latitude-randomLatIndex*multiplierFactor,latLng.longitude-randomLonIndex*multiplierFactor)
            }
            getBitmapFromURL(item, latLngList)
        }

        execute = true
    }


    fun getBitmapFromURL(src: String, latLng: LatLng){
        Picasso.get().load(src).into(object : com.squareup.picasso.Target {
            override fun onBitmapLoaded(bitmap: Bitmap?, from: Picasso.LoadedFrom?) {
                Log.d("bitmap", "this is my bitmap: "+bitmap)
                //it fills the markers with the images and random locations
                mMap.addMarker(MarkerOptions().position(latLng).icon(BitmapDescriptorFactory.fromBitmap(bitmap)))
            }

            override fun onPrepareLoad(placeHolderDrawable: Drawable?) {}

            override fun onBitmapFailed(e: Exception?, errorDrawable: Drawable?) {}
        })

    }


}
