package com.example.memeapp

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.View.VISIBLE
import android.widget.ImageView
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.bumptech.glide.Glide
import java.net.URL

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setTheme(R.style.memeAppTheme)
        loadmeme()
    }

    private fun loadmeme(){
        val queue = Volley.newRequestQueue(this)
        val url = "https://meme-api.herokuapp.com/gimme"
        val image = findViewById<ImageView>(R.id.memeImage)
//        progressBar.visibility = VISIBLE


        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET, url,null,
            Response.Listener{ response ->
            val url = response.getString("url")
                Glide.with(this)
                    .load(url)
                    .into(image)
            },
            Response.ErrorListener {  })

        queue.add(jsonObjectRequest)
    }

    fun nextMeme(view: View) {
        loadmeme()
    }
    fun shareMeme(view: View) {
        val intent = Intent(Intent.ACTION_SEND)
        intent.type = ("image/jpeg")
        intent.putExtra(Intent.EXTRA_STREAM, Uri.parse("https://meme-api.herokuapp.com/gimme"))
        val chooser = Intent.createChooser(intent,"Share this meme on ->")
        startActivity(chooser)
    }

}