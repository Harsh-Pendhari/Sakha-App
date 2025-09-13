package com.example.sakha

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.*

class WeatherActivity : AppCompatActivity() {

    private val apiKey = "399d4ae985dd3d49a2adebc6b5f83d53"  // penWeatherMap API key
    private val city = "Mumbai"

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
                "https://api.openweathermap.org/data/2.5/forecast/daily?q=$city&cnt=4&appid=$apiKey&units=metric"
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
                    val dayTemp = tempObj.getDouble("day")
                    val weather = item.getJSONArray("weather").getJSONObject(0)
                    val condition = weather.getString("main")

                    val cal = Calendar.getInstance()
                    cal.add(Calendar.DAY_OF_YEAR, i)
                    val dayName =
                        SimpleDateFormat("EEEE", Locale.getDefault()).format(cal.time)

                    forecasts.add(WeatherInfo(dayName, dayTemp, condition))
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

        todayBtn.text = "${forecasts[0].day}: ${forecasts[0].temperature}°C"
        tomorrowBtn.text = "${forecasts[1].day}: ${forecasts[1].temperature}°C"
        day1Btn.text = "${forecasts[2].day}: ${forecasts[2].temperature}°C"
        day2Btn.text = "${forecasts[3].day}: ${forecasts[3].temperature}°C"

        todayBtn.setCompoundDrawablesWithIntrinsicBounds(0, 0, getWeatherIcon(forecasts[0].condition), 0)
        tomorrowBtn.setCompoundDrawablesWithIntrinsicBounds(0, 0, getWeatherIcon(forecasts[1].condition), 0)
        day1Btn.setCompoundDrawablesWithIntrinsicBounds(0, 0, getWeatherIcon(forecasts[2].condition), 0)
        day2Btn.setCompoundDrawablesWithIntrinsicBounds(0, 0, getWeatherIcon(forecasts[3].condition), 0)
    }

    private fun getWeatherIcon(condition: String): Int {
        return when (condition.lowercase()) {
            "clouds" -> R.drawable.cloudy_icon
            "rain" -> R.drawable.rainy_icon
            "clear" -> R.drawable.sunny_icon
            "thunderstorm" -> R.drawable.storm_icon
            else -> R.drawable.partlycloudy_icon
        }
    }
}
