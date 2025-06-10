package com.example.mapsdktesting

import android.graphics.Bitmap
import android.graphics.Canvas
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.mapsdktesting.databinding.ActivityMainBinding
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import androidx.core.graphics.createBitmap
import com.google.android.gms.maps.model.Marker

class MainActivity : AppCompatActivity(), OnMapReadyCallback {
    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!
    private var map: GoogleMap? = null
    private var activeMarker: Marker? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val mapFragment = supportFragmentManager.findFragmentById(R.id.mapFragment) as? SupportMapFragment
        mapFragment?.getMapAsync(this)

        binding.rgMapType.setOnCheckedChangeListener { _, itemId ->
            when(itemId) {
                R.id.rbNormal -> {
                    map?.mapType = GoogleMap.MAP_TYPE_NORMAL
                }

                R.id.rbHybrid -> {
                    map?.mapType = GoogleMap.MAP_TYPE_HYBRID
                }

                R.id.rbSatellite -> {
                    map?.mapType = GoogleMap.MAP_TYPE_SATELLITE
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onMapReady(map: GoogleMap?) {
        this.map = map
        map?.mapType = GoogleMap.MAP_TYPE_NORMAL
        val yangonLatLong = LatLng(16.8409, 96.1735)
        val kyaikHtawLatLong = LatLng(16.5173, 95.8599)
        map?.moveCamera(CameraUpdateFactory.newLatLngZoom(yangonLatLong, 12f))
        map?.uiSettings?.isZoomControlsEnabled = true

        val markerOption = MarkerOptions()
        markerOption.title("Location")
        markerOption.snippet("Kyaik Htaw")
        markerOption.position(kyaikHtawLatLong)
        markerOption.icon(BitmapDescriptorFactory.fromBitmap(getBitmapFromDrawable(R.drawable.hospital_icon)))
        val marker = map?.addMarker(markerOption)
        marker?.tag = CustomWindowInfoData("Kyaik Htaw", "Testing", R.drawable.pin_long_hospital)
        map?.setInfoWindowAdapter(CustomWindowInfoAdapter(this))

        map?.setOnMarkerClickListener {
            activeMarker = marker
            marker?.showInfoWindow()
            true
        }

        map?.setOnMapClickListener {
            activeMarker?.hideInfoWindow()
        }

        map?.setOnCameraMoveListener {
            activeMarker?.hideInfoWindow()
        }
    }

    private fun getBitmapFromDrawable(resId: Int): Bitmap? {
        var bitmap: Bitmap? = null
        val drawable = ResourcesCompat.getDrawable(resources, resId, null)
        drawable?.let {
            bitmap = createBitmap(120, 120)
            val canvas = Canvas(bitmap)
            it.setBounds(0, 0, canvas.width, canvas.height)
            it.draw(canvas)
        }
        return bitmap
    }
}
