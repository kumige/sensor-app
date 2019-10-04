package com.example.sensorapp

import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_weather.*
import org.json.JSONObject
import java.math.BigDecimal
import java.math.RoundingMode
import java.net.URL
import java.text.SimpleDateFormat
import java.util.*


class WeatherFragment : Fragment() {

    val apiKey: String = BuildConfig.ApiKey
    val city: String = "helsinki,fi"

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_weather, container, false)
        return view
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
         weatherTask().execute()
    }

    inner class weatherTask : AsyncTask<String, Void, String>() {

        /*override fun onPreExecute() {
            super.onPreExecute()
            loader.visibility = View.VISIBLE
            mainLinearLayout.visibility = View.GONE
        }*/

        override fun doInBackground(vararg params: String?): String? {
            var response:String?
            try{
                response = URL("https://api.openweathermap.org/data/2.5/weather?q=$city&units=metric&appid=$apiKey").readText(
                    Charsets.UTF_8
                )
            }catch (e: Exception){
                response = null
            }
            return response
        }

        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)
            try {
                /* Extracting JSON returns from the API */
                val jsonObj = JSONObject(result)
                val main = jsonObj.getJSONObject("main")
                val sys = jsonObj.getJSONObject("sys")
                val wind = jsonObj.getJSONObject("wind")
                val weather = jsonObj.getJSONArray("weather").getJSONObject(0)

                val updatedAt: Long = jsonObj.getLong("dt")
                val updatedAtText =
                    "Updated at: " + SimpleDateFormat("dd/MM/yyyy hh:mm a", Locale.ENGLISH).format(
                        Date(updatedAt * 1000)
                    )
                val tempMin = "Min Temp: " + main.getString("temp_min") + "°C"
                val tempMax = "Max Temp: " + main.getString("temp_max") + "°C"
                val pressure = main.getString("pressure")
                val humidity = main.getString("humidity")

                val sunrise: Long = sys.getLong("sunrise")
                val sunset: Long = sys.getLong("sunset")
                val windSpeed = wind.getString("speed")
                val weatherDescription = weather.getString("description")
                val weatherSimple = weather.getString("main")
                //val temp = main.getString("temp") + "°C"

                val address = jsonObj.getString("name") + ", " + sys.getString("country")

                val tempINT = main.getString("temp")
                Log.d("weather", "tempint: ${tempINT}")
                val tempRounded = BigDecimal(tempINT).setScale(0, RoundingMode.HALF_EVEN).toString()
                Log.d("weather", "temprounded: ${tempRounded}")

                val tempString = "$tempRounded°C"
                Log.d("weather", "${weatherSimple}, ${weatherDescription}")

                weatherColors(weatherSimple, weatherDescription)
                textView_temperature.text = tempString
                textView_weather.text = weatherDescription
                textView_weatherLocation.text = city

                loader.visibility = View.GONE
                mainLinearLayout.visibility = View.VISIBLE

            } catch (e: Exception) {
            }
        }
    }

    private fun weatherColors(simpleWeather: String, weatherDesc: String){
        when(simpleWeather){
            "Thunderstorm" -> {
                weather_icon.setImageResource(R.drawable.thunder)
                weather_background.setBackgroundResource(R.drawable.weather_background_thunder)
                Log.d("weather", "thunder")
            }

            "Drizzle" -> {
                weather_icon.setImageResource(R.drawable.rain2)
                weather_background.setBackgroundResource(R.drawable.weather_background_rain)
                Log.d("weather","tihkusade")
            }

            "Rain" -> {
                weather_icon.setImageResource(R.drawable.rain2)
                weather_background.setBackgroundResource(R.drawable.weather_background_rain)
                Log.d("weather", "Rain")
            }

            "Snow" -> {
                weather_icon.setImageResource(R.drawable.snow)
                weather_background.setBackgroundResource(R.drawable.weather_background_snow)
                Log.d("weather", "snow")
            }

            "Clear" -> {
                weather_icon.setImageResource(R.drawable.sunny)
                weather_background.setBackgroundResource(R.drawable.weather_background_sunny)
                Log.d("weather", "aurinkoista")
            }

            "Clouds" -> {
                // LISÄÄ PUOLIPILVINEN
                if(weatherDesc == "few clouds: 11-25%") {
                weather_icon.setImageResource(R.drawable.cloudy)
                weather_background.setBackgroundResource(R.drawable.weather_background_cloudy)
                Log.d("weather", "cloudy")
                } else {
                weather_icon.setImageResource(R.drawable.cloudy)
                weather_background.setBackgroundResource(R.drawable.weather_background_cloudy)
                Log.d("weather", "cloudy")
                }
            }

            "Mist" -> {
                weather_icon.setImageResource(R.drawable.misty)
                weather_background.setBackgroundResource(R.drawable.weather_background_misty)
                Log.d("weather","MIST")
            }

            "Smoke" -> {
                weather_icon.setImageResource(R.drawable.misty)
                weather_background.setBackgroundResource(R.drawable.weather_background_misty)
                Log.d("weather","MIST")
            }

            "Haze" -> {
                weather_icon.setImageResource(R.drawable.misty)
                weather_background.setBackgroundResource(R.drawable.weather_background_misty)
                Log.d("weather","MIST")
            }

            "Dust" -> {
                weather_icon.setImageResource(R.drawable.misty)
                weather_background.setBackgroundResource(R.drawable.weather_background_misty)
                Log.d("weather","MIST")
            }

            "Fog" -> {
                weather_icon.setImageResource(R.drawable.misty)
                weather_background.setBackgroundResource(R.drawable.weather_background_misty)
                Log.d("weather","MIST")
            }

            "Sand" -> {
                weather_icon.setImageResource(R.drawable.misty)
                weather_background.setBackgroundResource(R.drawable.weather_background_misty)
                Log.d("weather","MIST")
            }

            "Dust" -> {
                weather_icon.setImageResource(R.drawable.misty)
                weather_background.setBackgroundResource(R.drawable.weather_background_misty)
                Log.d("weather","MIST")
            }

            "Ash" -> {
                weather_icon.setImageResource(R.drawable.misty)
                weather_background.setBackgroundResource(R.drawable.weather_background_misty)
                Log.d("weather","MIST")
            }

            "Squall" -> {
                weather_icon.setImageResource(R.drawable.misty)
                weather_background.setBackgroundResource(R.drawable.weather_background_misty)
                Log.d("weather","MIST")
            }

            "Tornado" -> {
                weather_icon.setImageResource(R.drawable.misty)
                weather_background.setBackgroundResource(R.drawable.weather_background_misty)
                Log.d("weather","MIST")
            }

            else -> Log.d("weather","Set default")
        }
    }
}