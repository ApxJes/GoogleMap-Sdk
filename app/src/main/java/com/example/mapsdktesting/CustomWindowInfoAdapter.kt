package com.example.mapsdktesting

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.Marker

class CustomWindowInfoAdapter(
    private val context: Context
): GoogleMap.InfoWindowAdapter {

    @SuppressLint("InflateParams")
    override fun getInfoWindow(marker: Marker?): View? {
        val view = LayoutInflater.from(context)
            .inflate(R.layout.custom_window_info, null)

        val title: TextView = view.findViewById(R.id.txvHospitalName)
        val des: TextView = view.findViewById(R.id.txvHospitalDes)
        val image: ImageView = view.findViewById(R.id.imvHospital)

        val customWindowData = marker?.tag as? CustomWindowInfoData ?: return null
        title.text = customWindowData.title
        des.text = customWindowData.des
        customWindowData.image.let {
            image.setImageResource(it)
        }

        return view
    }

    override fun getInfoContents(p0: Marker?): View? {
        return null
    }
}