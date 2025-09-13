package com.example.sakha

import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.*

class WeatherActivity : AppCompatActivity() {

    private val apiKey = "YOUR_API_KEY"  //OpenWeatherMap API key
    private val lat = "19.0760"
    private val lon = "72.8777"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_weather)

        ViewCompat.setOnApplyWindowInsetsListener(window.decorView) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        fetchWeather()
    }

    private fun fetchWeather() {
        Thread {
            val client = OkHttpClient()
            val url =
                "https://api.openweathermap.org/data/2.5/forecast/daily?lat=$lat&lon=$lon&cnt=4&appid=$apiKey&units=metric"
            val request = Request.Builder().url(url).build()
            val response = client.newCall(request).execute()
            val data = response.body?.string()

            data?.let {
                val json = JSONObject(it)
                val list = json.getJSONArray("list")

                val forecasts = mutableListOf<WeatherInfo>()
                for (i in 0 until list.length()) {
                    val item = list.getJSONObject(i)
                    val tempObj = item.getJSONObject("temp")
                    val minTemp = tempObj.getDouble("min")
                    val maxTemp = tempObj.getDouble("max")
                    val weather = item.getJSONArray("weather").getJSONObject(0)
                    val condition = weather.getString("main")

                    val cal = Calendar.getInstance()
                    cal.add(Calendar.DAY_OF_YEAR, i)
                    val dayName =
                        if (i == 0) "Today" else SimpleDateFormat("EEEE", Locale.getDefault()).format(cal.time)

                    forecasts.add(WeatherInfo(dayName, minTemp, maxTemp, condition))
                }

                runOnUiThread {
                    updateUI(forecasts)
                }
            }
        }.start()
    }

    private fun updateUI(forecasts: List<WeatherInfo>) {
        val todayBtn: Button = findViewById(R.id.today)
        val tomorrowBtn: Button = findViewById(R.id.tomorrow)
        val day1Btn: Button = findViewById(R.id.day1)
        val day2Btn: Button = findViewById(R.id.agriculture)

        todayBtn.text = "${forecasts[0].day}  ${forecasts[0].maxTemp}°C / ${forecasts[0].minTemp}°C"
        tomorrowBtn.text = "${forecasts[1].day}  ${forecasts[1].maxTemp}°C / ${forecasts[1].minTemp}°C"
        day1Btn.text = "${forecasts[2].day}  ${forecasts[2].maxTemp}°C / ${forecasts[2].minTemp}°C"
        day2Btn.text = "${forecasts[3].day}  ${forecasts[3].maxTemp}°C / ${forecasts[3].minTemp}°C"

        todayBtn.setCompoundDrawablesWithIntrinsicBounds(0, 0, getWeatherIcon(forecasts[0].condition), 0)
        tomorrowBtn.setCompoundDrawablesWithIntrinsicBounds(0, 0, getWeatherIcon(forecasts[1].condition), 0)
        day1Btn.setCompoundDrawablesWithIntrinsicBounds(0, 0, getWeatherIcon(forecasts[2].condition), 0)
        day2Btn.setCompoundDrawablesWithIntrinsicBounds(0, 0, getWeatherIcon(forecasts[3].condition), 0)

        // Optional: Show current weather at top
        val currentTemp: TextView = findViewById(R.id.currentTemp)
        val currentCondition: TextView = findViewById(R.id.currentCondition)
        val weatherIcon: ImageView = findViewById(R.id.weatherIcon)

        currentTemp.text = "${forecasts[0].maxTemp.toInt()}°C"
        currentCondition.text = forecasts[0].condition
        weatherIcon.setImageResource(getWeatherIcon(forecasts[0].condition))
    }

    private fun getWeatherIcon(condition: String): Int {
        return when (condition.lowercase()) {
            "clouds" -> R.drawable.cloud_icon
            "rain" -> R.drawable.rainy_icon
            "clear" -> R.drawable.sunny_icon
            "thunderstorm" -> R.drawable.storm_icon
            else -> R.drawable.cloud_icon
        }
    }
}
