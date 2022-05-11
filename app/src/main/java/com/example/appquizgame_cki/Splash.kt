package com.example.appquizgame_cki

import android.content.Intent
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.airbnb.lottie.LottieAnimationView
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class Splash : AppCompatActivity() {
    var start_animation: Animation? = null
    var splashText_animation: Animation? = null
    var splashText1_animation: Animation? = null
    var logo_animation: Animation? = null
    private var checkvol:Boolean = true
    private lateinit var mediaPlayer : MediaPlayer
    var chek : Boolean = true
    override fun onCreate(savedInstanceState: Bundle?) {
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        ) ///Eneter into fullscreen mode

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        //Animations
        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//        start_btn = findViewById(R.id.start_btn)
//        txtSplashText = findViewById(R.id.sp_tv)
//        txtSplashText1 = findViewById(R.id.sp_tv1)
        val start_btn = findViewById<Button>(R.id.start_btn)
        val txtSplashText = findViewById<TextView>(R.id.sp_tv)
        val txtSplashText1 = findViewById<TextView>(R.id.sp_tv1)
        val logo_imageView = findViewById<ImageView>(R.id.sp_logo)

        val lottieAnimationView = findViewById<LottieAnimationView>(R.id.Logloading)

        //Animations
        start_animation = AnimationUtils.loadAnimation(this, R.anim.anim_bottom)
        splashText_animation = AnimationUtils.loadAnimation(this, R.anim.anim_slide_in_left)
        splashText1_animation = AnimationUtils.loadAnimation(this, R.anim.anim_slide_in_right)
        logo_animation = AnimationUtils.loadAnimation(this, R.anim.anim_top)

        start_btn.setAnimation(start_animation)
        logo_imageView.setAnimation(logo_animation)
        txtSplashText1.setAnimation(splashText1_animation)
        txtSplashText.setAnimation(splashText_animation)

        start_btn.setOnClickListener(View.OnClickListener {
            lottieAnimationView.setVisibility(View.VISIBLE)
            startActivity(Intent(applicationContext, Main_Menu::class.java))
        })
        music()

    }
    private fun music() {
        mediaPlayer = MediaPlayer.create(this, R.raw.bg_music)
        mediaPlayer.isLooping = true
        mediaPlayer.start()
    }

    override fun onRestart() {
        super.onRestart()
    }
    override fun onBackPressed() {
        super.onBackPressed()
    }

}