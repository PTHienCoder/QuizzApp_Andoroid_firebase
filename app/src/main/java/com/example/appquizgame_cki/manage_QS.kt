package com.example.appquizgame_cki

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class manage_QS : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_manage_qs)
        val manage = findViewById<FloatingActionButton>(R.id.fabbbb)
        manage.setOnClickListener(View.OnClickListener {
            startActivity(Intent(applicationContext, Add_QS::class.java))
        })

        val lll = findViewById<Button>(R.id.action_menu_presenter)

        lll.setOnClickListener(View.OnClickListener {
            startActivity(Intent(applicationContext, Main_Menu::class.java))
        })
        val btnback : Button = findViewById<Button?>(R.id. action_menu_presenter)
        btnback.setOnClickListener{
            onBackPressed()
        }


        val start_btn1 = findViewById<TextView>(R.id.btn1)
        val start_btn2 = findViewById<TextView>(R.id.btn2)
        val start_btn4 = findViewById<TextView>(R.id.btn4)
        val start_btn5 = findViewById<TextView>(R.id.btn5)
        start_btn1.setOnClickListener(View.OnClickListener {
            var intent= Intent(this,ViewAll_QS::class.java)
            intent.putExtra("Category","a" )
            startActivity(intent)
            finish()

        })
        start_btn2.setOnClickListener(View.OnClickListener {
            var intent= Intent(this,ViewAll_QS::class.java)
            intent.putExtra("Category","b" )
            startActivity(intent)
            finish()
        })

        start_btn4.setOnClickListener(View.OnClickListener {
            var intent= Intent(this,ViewAll_QS::class.java)
            intent.putExtra("Category","c" )
            startActivity(intent)
            finish()
        })
        start_btn5.setOnClickListener(View.OnClickListener {
            var intent= Intent(this,ViewAll_QS::class.java)
            intent.putExtra("Category","d" )
            startActivity(intent)
            finish()
        })

    }
    override fun onBackPressed() {
        super.onBackPressed()
    }
}