package com.nasikhunamin.mybackgroundthread

import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.Exception
import java.lang.ref.WeakReference

class MainActivity : AppCompatActivity(), MyAsyncCallback {
    companion object{
        private const val INPUT_STRING = "Halo ini demo Asynctask."
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val demoAsync = DemoAsync(this)
        demoAsync.execute(INPUT_STRING)
    }

    override fun onPreExcute() {
        tv_status.setText(R.string.status_pre)
        tv_desc.text = INPUT_STRING
    }

    override fun onPostExcute(text: String) {
        tv_status.setText(R.string.status_post)
        tv_desc.text = text
    }
    // param : menerima inputan objek string (String)
    // progress : tidak menampilkan progress (Void)
    // result : hasil dari background thread (String)
    private class DemoAsync(val listener : MyAsyncCallback) : AsyncTask<String, Void, String>() {
        companion object {
            private val LOG_ASYNC = "DemoAsync"
        }
        // weakreference digunakan untuk menghindari memory leak yang terjad didalam AsyncTask
        private val myListener : WeakReference<MyAsyncCallback>
        init {
            this.myListener = WeakReference(listener)
        }

        override fun onPreExecute() {
            super.onPreExecute()
            Log.d(LOG_ASYNC, "status : onPreExcute")

            val myListener = myListener.get()
            myListener?.onPreExcute()
        }

        override fun doInBackground(vararg params: String?): String {
            Log.d(LOG_ASYNC, "status : doInBackground")
            var output : String? = null

            try {
                // mengambil value yang diberikan pada saat excute background thread
                val input = params[0]
                output = "$input Selamat belajar!!"
                Thread.sleep(2000L)
            }catch (ex : Exception){
                Log.d(LOG_ASYNC, ex.message.toString())
            }

            return output.toString()
        }

        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)
            Log.d(LOG_ASYNC, "status : onPostExcute")

            val myListener = myListener.get()
            myListener?.onPostExcute(result.toString())
        }
    }
}

internal interface MyAsyncCallback{
    fun onPreExcute()
    fun onPostExcute(text: String)
}