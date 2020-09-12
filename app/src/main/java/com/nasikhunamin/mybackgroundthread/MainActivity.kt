package com.nasikhunamin.mybackgroundthread

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.*
import java.lang.Exception

class MainActivity : AppCompatActivity() {
    companion object{
        private const val INPUT_STRING = "Halo ini demo Asynctask."
        private val LOG_ASYNC = "DemoAsync"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tv_status.setText(R.string.status_pre)
        tv_desc.text = INPUT_STRING
        GlobalScope.launch(Dispatchers.IO) {
            val input = INPUT_STRING
            var output: String? = null
            Log.d(LOG_ASYNC, "status : doInBackground")
            try {
                output = "$input Selamat Belajar!!"
                delay(2000L)

                //pindah ke main thread
                withContext(Dispatchers.Main){
                    tv_status.setText(R.string.status_post)
                    tv_desc.text = output
                }
            }catch (ex: Exception){
                Log.d(LOG_ASYNC, ex.message.toString())
            }
        }
    }
}
