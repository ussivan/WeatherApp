package com.example.weatherapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    lateinit var call: Call<DailyForecasts>
    val TOKEN = "8LV1xWYsluZir3BmUH14fjNCSACl3XgE"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if(savedInstanceState != null) {
            temp.text = savedInstanceState.getString(getString(R.string.temp))
        }
        run()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(getString(R.string.temp), temp.text.toString())
    }

    private fun run() {
        call = MyApp.app.weatherApi.getRepos("294021", TOKEN)
        call?.enqueue(object : Callback<DailyForecasts> {
            override fun onFailure(call: Call<DailyForecasts>, t: Throwable) {
                val toast = Toast.makeText(
                    applicationContext,
                    "Connection troubles", Toast.LENGTH_SHORT
                )
                toast.show()
            }

            override fun onResponse(call: Call<DailyForecasts>, response: Response<DailyForecasts>) {
                val body = response.body()
                if (body != null) {
                    temp.text = getString(R.string.format, body.dailyForecasts[0].temperature.min.value)
                    rft.text = getString(R.string.format, body.dailyForecasts[0].rftemperature.min.value)
                    prec.text = getString(R.string.format, body.dailyForecasts[0].day.precipitation)
                    wind.text = getString(R.string.format, body.dailyForecasts[0].day.wind.speed.value)
                    Log.println(Log.DEBUG, "nya" , temp.text.toString())
                }
            }
        })
    }
}
