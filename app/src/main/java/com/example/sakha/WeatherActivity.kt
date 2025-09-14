package com.example.sakha

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.button.MaterialButton
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.net.URL
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.LinkedHashMap
import kotlin.math.max
import kotlin.math.min

class WeatherActivity : AppCompatActivity() {

    private val apiKey = "399d4ae985dd3d49a2adebc6b5f83d53"

    private val latitude = 19.0760
    private val longitude = 72.8777

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_weather)

        ViewCompat.setOnApplyWindowInsetsListener(window.decorView) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        fetchForecast()
    }

    private fun fetchForecast() {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val url =
                    "https://api.openweathermap.org/data/2.5/forecast?lat=$latitude&lon=$longitude&units=metric&appid=$apiKey"
                val response = URL(url).readText()
                // Log.d("WEATHER_API", response) debug

                val json = JSONObject(response)
                val list = json.getJSONArray("list")

                val dateGroups = LinkedHashMap<String, MutableList<JSONObject>>()
                val dtFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())

                for (i in 0 until list.length()) {
                    val item = list.getJSONObject(i)
                    val dtTxt = item.optString("dt_txt", null)
                    val dateKey = if (dtTxt != null && dtTxt.length >= 10) {
                        dtTxt.substring(0, 10) // yyyy-MM-dd
                    } else {
                        // fallback: parse epoch 'dt' field
                        val dt = item.optLong("dt", 0L) * 1000L
                        SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date(dt))
                    }
                    val group = dateGroups.getOrPut(dateKey) { mutableListOf() }
                    group.add(item)
                }

                val forecasts = mutableListOf<WeatherInfo>()
                val todayKey = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())

                val dateKeys = dateGroups.keys.filter { it >= todayKey }.toList()
                for (key in dateKeys) {
                    if (forecasts.size >= 4) break

                    val entries = dateGroups[key]!!
                    var minTemp = Double.MAX_VALUE
                    var maxTemp = Double.MIN_VALUE
                    var condition = "Unknown"

                    // Iterate through all 3-hour entries for the day to find the min, max, and the condition
                    for (entry in entries) {
                        val main = entry.getJSONObject("main")
                        val temp = main.getDouble("temp")
                        minTemp = min(minTemp, temp)
                        maxTemp = max(maxTemp, temp)
                    }

                    // Find the condition for the day, for example, from the noon entry
                    val noonEntry = entries.find { e ->
                        val dtTxt = e.optString("dt_txt", "")
                        dtTxt.contains("12:00:00")
                    }
                    val chosen = noonEntry ?: entries[entries.size / 2] // pick a middle element
                    val weatherArr = chosen.getJSONArray("weather")
                    condition = weatherArr.getJSONObject(0).getString("main")

                    val label = when {
                        key == todayKey -> "Today"
                        isTomorrowKey(key) -> "Tomorrow"
                        else -> {
                            // convert key (yyyy-MM-dd) to weekday
                            val d = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse(key)
                            SimpleDateFormat("EEEE", Locale.getDefault()).format(d!!)
                        }
                    }

                    forecasts.add(WeatherInfo(label, minTemp, maxTemp, condition))
                }

                withContext(Dispatchers.Main) {
                    updateUI(forecasts)
                }
            } catch (e: Exception) {
                Log.e("WEATHER_API_ERR", "Fetch failed", e)
                withContext(Dispatchers.Main) {
                    Toast.makeText(this@WeatherActivity, "Weather fetch failed", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun isTomorrowKey(dateKey: String): Boolean {
        val fmt = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val cal = Calendar.getInstance()
        cal.add(Calendar.DAY_OF_YEAR, 1)
        val tomorrow = fmt.format(cal.time)
        return tomorrow == dateKey
    }

    private fun updateUI(forecasts: List<WeatherInfo>) {
        val todayBtn: MaterialButton = findViewById(R.id.today)
        val tomorrowBtn: MaterialButton = findViewById(R.id.tomorrow)
        val day1Btn: MaterialButton = findViewById(R.id.day1)
        val day2Btn: MaterialButton = findViewById(R.id.day2)

        // apply forecasts safely
        if (forecasts.isNotEmpty()) {
            if (forecasts.size > 0) {
                todayBtn.text = "${forecasts[0].day}: ${formatTemp(forecasts[0].minTemperature, forecasts[0].maxTemperature)}"
                todayBtn.setIconResource(getWeatherIconRes(forecasts[0].condition))
            } else {
                todayBtn.text = "Today"
                todayBtn.setIconResource(0) // Clear icon
            }

            if (forecasts.size > 1) {
                tomorrowBtn.text = "${forecasts[1].day}: ${formatTemp(forecasts[1].minTemperature, forecasts[1].maxTemperature)}"
                tomorrowBtn.setIconResource(getWeatherIconRes(forecasts[1].condition))
            } else {
                tomorrowBtn.text = "Tomorrow"
                tomorrowBtn.setIconResource(0)
            }

            if (forecasts.size > 2) {
                day1Btn.text = "${forecasts[2].day}: ${formatTemp(forecasts[2].minTemperature, forecasts[2].maxTemperature)}"
                day1Btn.setIconResource(getWeatherIconRes(forecasts[2].condition))
            } else {
                day1Btn.text = "" // Clear text
                day1Btn.setIconResource(0)
            }

            if (forecasts.size > 3) {
                day2Btn.text = "${forecasts[3].day}: ${formatTemp(forecasts[3].minTemperature, forecasts[3].maxTemperature)}"
                day2Btn.setIconResource(getWeatherIconRes(forecasts[3].condition))
            } else {
                day2Btn.text = ""
                day2Btn.setIconResource(0)
            }
        } else {
            Toast.makeText(this, "No forecast data available", Toast.LENGTH_SHORT).show()
        }
    }

    private fun formatTemp(minTemp: Double, maxTemp: Double): String {
        return String.format(Locale.getDefault(), "%.0f\u00B0C / %.0f\u00B0C", minTemp, maxTemp)
    }

    private fun getWeatherIconRes(condition: String): Int {
        return when (condition.lowercase(Locale.getDefault())) {
            "clouds" -> R.drawable.cloud_icon
            "rain", "drizzle" -> R.drawable.rainy_icon
            "clear" -> R.drawable.sunny_icon
            "thunderstorm" -> R.drawable.storm_icon
            else -> R.drawable.cloud_icon
        }
    }
}