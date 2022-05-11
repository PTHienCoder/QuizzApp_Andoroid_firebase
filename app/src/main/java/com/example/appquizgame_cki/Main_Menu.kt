package com.example.appquizgame_cki

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import java.util.*

class Main_Menu : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_menu)



        val start_btn1 = findViewById<Button>(R.id.btn1)
        val start_btn2 = findViewById<Button>(R.id.btn2)
        val start_btn4 = findViewById<Button>(R.id.btn4)
        val start_btn5 = findViewById<Button>(R.id.btn5)
        val manage = findViewById<Button>(R.id.action_menu_presenter)
        manage.setOnClickListener(View.OnClickListener {
            startActivity(Intent(applicationContext, manage_QS::class.java))

        })
        start_btn1.setOnClickListener(View.OnClickListener {
            var intent= Intent(applicationContext,Choose_Level::class.java)
            intent.putExtra("Category","a" )
            startActivity(intent)
            finish()

        })
        start_btn2.setOnClickListener(View.OnClickListener {
            var intent= Intent(applicationContext,Choose_Level::class.java)
            intent.putExtra("Category","b" )
            startActivity(intent)
            finish()
        })

        start_btn4.setOnClickListener(View.OnClickListener {
            var intent= Intent(applicationContext,Choose_Level::class.java)
            intent.putExtra("Category","c" )
            startActivity(intent)
            finish()
        })
        start_btn5.setOnClickListener(View.OnClickListener {
            var intent= Intent(applicationContext,Choose_Level::class.java)
            intent.putExtra("Category","d" )
            startActivity(intent)
            finish()
        })


    }
}