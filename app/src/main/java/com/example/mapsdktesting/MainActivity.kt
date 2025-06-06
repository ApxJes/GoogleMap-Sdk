package com.example.mapsdktesting

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.mapsdktesting.databinding.ActivityMainBinding
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng

class MainActivity : AppCompatActivity(), OnMapReadyCallback {
    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!
    private var map: GoogleMap? = null

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

        val mapFragment =
            supportFragmentManager.findFragmentById(R.id.map_fragment) as? SupportMapFragment

        mapFragment?.getMapAsync(this)

        val radioGroup = binding.radioGroup
        radioGroup.setOnCheckedChangeListener { _, itemId->
            when(itemId) {
                R.id.btnNormal -> {
                    map?.mapType = GoogleMap.MAP_TYPE_NORMAL
                }
                R.id.btnHybrid -> {
                    map?.mapType = GoogleMap.MAP_TYPE_HYBRID
                }
            }
        }
    }

    override fun onMapReady(map: GoogleMap?) {
        this.map = map
        map?.mapType = GoogleMap.MAP_TYPE_NORMAL
        map?.uiSettings?.isZoomControlsEnabled = true
        val latLng = LatLng(16.8409 , 96.1735)
        map?.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 10f))
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}