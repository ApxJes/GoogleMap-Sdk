package com.example.mapsdktesting

import android.graphics.Bitmap
import android.graphics.Canvas
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import androidx.core.graphics.createBitmap
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

        val yangonLatLong = LatLng(16.8409, 96.1735)
        val pinLongLatLong = LatLng(16.8608, 96.2087)
        val generalHospital = LatLng(16.7791, 96.1490)
        val punHlaingHospital = LatLng(16.840791, 96.089114)
        map?.moveCamera(CameraUpdateFactory.newLatLngZoom(yangonLatLong, 11f))


        val pinLongHospitalMarker = MarkerOptions()
        pinLongHospitalMarker.position(pinLongLatLong)
        pinLongHospitalMarker.title("Location")
        pinLongHospitalMarker.snippet("Pin Long Hospital")
        pinLongHospitalMarker.icon(BitmapDescriptorFactory.fromBitmap(getBitMapFromDrawable(R.drawable.hospital_icon)))
        map?.addMarker(pinLongHospitalMarker)

        val generalHospitalMarker = MarkerOptions()
        generalHospitalMarker.position(generalHospital)
        generalHospitalMarker.title("Location")
        generalHospitalMarker.snippet("General Hospital")
        generalHospitalMarker.icon(BitmapDescriptorFactory.fromBitmap(getBitMapFromDrawable(R.drawable.hospital_icon)))
        map?.addMarker(generalHospitalMarker)

        val punHlaingHospitalMarker = MarkerOptions()
        punHlaingHospitalMarker.position(punHlaingHospital)
        punHlaingHospitalMarker.title("Location")
        punHlaingHospitalMarker.snippet("Pun Hlaing Hospital")
        punHlaingHospitalMarker.icon(BitmapDescriptorFactory.fromBitmap(getBitMapFromDrawable(R.drawable.hospital_icon)))
        map?.addMarker(punHlaingHospitalMarker)

    }

    private fun getBitMapFromDrawable(resId: Int): Bitmap? {
        var bitmap: Bitmap? = null
        val drawable = ResourcesCompat.getDrawable(resources, resId, null)
        if(drawable != null) {
            bitmap = createBitmap(120, 120, Bitmap.Config.ARGB_8888)
            val canvas = Canvas(bitmap)
            drawable.setBounds(0, 0, canvas.width, canvas.height)
            drawable.draw(canvas)
        }

        return bitmap
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}