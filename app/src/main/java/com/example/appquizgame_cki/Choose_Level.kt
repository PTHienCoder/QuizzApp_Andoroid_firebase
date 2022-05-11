package com.example.appquizgame_cki

import android.content.Intent
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView

class Choose_Level : AppCompatActivity() {
    private lateinit var mediaPlayer : MediaPlayer
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_choose_level)

        val cate =intent.getStringExtra("Category")
        val start_btn1 = findViewById<TextView>(R.id.lv1)
        val start_btn2 = findViewById<TextView>(R.id.lv2)
        val start_btn3 = findViewById<TextView>(R.id.lv3)


        start_btn1.setOnClickListener(View.OnClickListener {
            var intent= Intent(this,MainActivity::class.java)
            intent.putExtra("CAT",cate.toString())
            intent.putExtra("level","1" )
            startActivity(intent)
            music3()
            finish()
        })
        start_btn2.setOnClickListener(View.OnClickListener {
            var intent= Intent(this,MainActivity::class.java)
            intent.putExtra("CAT",cate.toString())
            intent.putExtra("level","2" )
            startActivity(intent)
            music3()
            finish()
        })
        start_btn3.setOnClickListener(View.OnClickListener {
            var intent= Intent(applicationContext,MainActivity::class.java)
            intent.putExtra("CAT",cate.toString())
            intent.putExtra("level","3" )
            startActivity(intent)
            music3()
            finish()
        })
        val btnback : Button = findViewById<Button?>(R.id. action_menu_presenter)
        btnback.setOnClickListener{
            onBackPressed()
        }


    }
    private fun music3() {
        mediaPlayer = MediaPlayer.create(this, R.raw.level_lose)
        mediaPlayer.start()
    }
    override fun onBackPressed() {
        super.onBackPressed()
    }
}