package com.example.weatherapp

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import org.json.JSONObject
import kotlin.math.ceil

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val lat = intent.getStringExtra("lat")
        val long = intent.getStringExtra("long")
        window.statusBarColor= Color.parseColor("#1383C3")
        getJsonData(lat,long)
    }

    private fun getJsonData(lat:String?, long:String?){
        val API_KEY = "41ad72fb7c6ca7464cfc0a07d9348636"
        val queue = Volley.newRequestQueue(this)
        val url = "https://api.openweathermap.org/data/2.5/weather?lat=${lat}&lon=${long}&appid=${API_KEY}"

// Request a string response from the provided URL.
        val jsonRequest = JsonObjectRequest(
            Request.Method.GET, url,null,
            Response.Listener { response ->
                setValues(response)
            },
            Response.ErrorListener {Toast.makeText(this,"ERROR",Toast.LENGTH_LONG).show()})

// Add the request to the RequestQueue.
        queue.add(jsonRequest)
    }
    @SuppressLint("SetTextI18n")
    private fun setValues(response: JSONObject){
        val city = findViewById<TextView>(R.id.city)
        val coordinate = findViewById<TextView>(R.id.coordinates)
        val weather = findViewById<TextView>(R.id.weather)
        val temp = findViewById<TextView>(R.id.temp)
        val min_temp = findViewById<TextView>(R.id.min_temp)
        val max_temp = findViewById<TextView>(R.id.max_temp)
        val pressure = findViewById<TextView>(R.id.pressure)
        val humidity = findViewById<TextView>(R.id.humidity)
        val wind = findViewById<TextView>(R.id.wind)
        val degree = findViewById<TextView>(R.id.degree)
//        val gust = findViewById<TextView>(R.id.gust)
        city.text=response.getString("name")
        val lat = response.getJSONObject("coord").getString("lat")
        val long = response.getJSONObject("coord").getString("lon")
        coordinate.text="${lat},${long}"
        weather.text = response.getJSONArray("weather").getJSONObject(0).getString("main")
        var tempr = response.getJSONObject("main").getString("temp")
        tempr = ((((tempr).toFloat() - 273.15)).toInt()).toString()
        temp.text = "$tempr°C"
        var mintemp = response.getJSONObject("main").getString("temp_min")
        mintemp = ((((mintemp).toFloat() - 273.15)).toInt()).toString()
        min_temp.text = "$mintemp°C"
        val maxtemp = response.getJSONObject("main").getString("temp_max")
        mintemp = ((ceil((maxtemp).toFloat() - 273.15)).toInt()).toString()
        max_temp.text = "$maxtemp°C"
        pressure.text = response.getJSONObject("main").getString("pressure")
        humidity.text = response.getJSONObject("main").getString("humidity") + "%"
        wind.text = response.getJSONObject("wind").getString("speed")
        degree.text = "Degree: "+response.getJSONObject("wind").getString("deg") + "°"
//        gust.text = "Gust: "+response.getJSONObject("wind").getString("gust") + "km/h"
    }
}