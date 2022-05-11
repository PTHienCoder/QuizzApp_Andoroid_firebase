package com.example.appquizgame_cki

import android.app.Dialog
import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.view.Window
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.google.firebase.database.*
import com.squareup.picasso.Picasso


class MainActivity : AppCompatActivity(), View.OnClickListener{

    private lateinit var dbref: DatabaseReference
    private var currentPosition:Int=0

      var QSArrayList:ArrayList<Model_QS> = arrayListOf()
    private var selecedOption:Int=0
    private var score:Int=0
    private var aws:String ? = null

    private lateinit var mediaPlayer : MediaPlayer
    var cate : String ? =null
    var level : String ? =null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        cate =intent.getStringExtra("CAT")
        level =intent.getStringExtra("level")

        val op1: TextView = findViewById(R.id.op1)
        val op2: TextView = findViewById(R.id.op2)
        val op3: TextView = findViewById(R.id.op3)
        val op4: TextView = findViewById(R.id.op4)
        op1.setOnClickListener(this)
        op2.setOnClickListener(this)
        op3.setOnClickListener(this)
        op4.setOnClickListener(this)
        val btnback : Button = findViewById<Button?>(R.id. action_menu_presenter)
        btnback.setOnClickListener{
            onBackPressed()
        }


        getQSData()

    }
    private fun getQSData() {

        dbref = FirebaseDatabase.getInstance().getReference("Question")

        dbref.addValueEventListener(object : ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) {

                if (snapshot.exists()){

                    for (jh in snapshot.children){
                        val qs = jh.child("qs").getValue().toString()
                        val id = jh.child("type").getValue().toString()
                         val image = jh.child("image").getValue().toString()
                         val op11 = jh.child("op1").getValue().toString()
                         val op22 = jh.child("op2").getValue().toString()
                         val op33 = jh.child("op3").getValue().toString()
                         val op44 = jh.child("op4").getValue().toString()
                         val awss = jh.child("aws").getValue().toString()
                        val listQS = Model_QS(qs,image,op11,op22,op33,op44,awss)
                        if(id == cate){
                            QSArrayList.add(listQS)
                        }

                    }

                    setQuestion()
                }

            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }


        })

    }
    private fun setQuestion(){
        val stt : TextView = findViewById(R.id.no_of_questions_view)

        val Qs_content : TextView = findViewById(R.id.question_view)
        val Image_QS : ImageView = findViewById(R.id.image_qs)
        val op1 : TextView = findViewById(R.id.op1)
        val op2 : TextView = findViewById(R.id.op2)
        val op3 : TextView = findViewById(R.id.op3)
        val op4 : TextView = findViewById(R.id.op4)
        op1.setBackgroundResource(R.drawable.options_btn_bg)
        op2.setBackgroundResource(R.drawable.options_btn_bg)
        op3.setBackgroundResource(R.drawable.options_btn_bg)
        op4.setBackgroundResource(R.drawable.options_btn_bg)
        val question = QSArrayList!![currentPosition]
        stt.text = currentPosition.toString() + "/10"
        aws = question.awss
        Qs_content.text=question.qs + " ?"

       if(question.image.equals("NoImage")){
           Picasso.get().load(R.drawable.ic_qs_noimage).into(Image_QS)
       }else{
           Picasso.get().load(question.image).into(Image_QS)
       }
        op1.text=question.opp1
        op2.text=question.opp2
        op3.text=question.opp3
        op4.text=question.opp4

        showoptionlevel()




    }
    override fun onBackPressed() {
        super.onBackPressed()
    }
    override fun onClick(p0: View?) {

        when (p0?.id) {
            R.id.op1 -> {
                val op1: TextView = findViewById(R.id.op1)
                val selectusser:String = "op1"
                selecedOption = currentPosition+1
                op1.setBackgroundResource(R.drawable.selected_question_option)
                Checkaws(selectusser, op1)
            }

            R.id.op2->{
                val op2: TextView = findViewById(R.id.op2)
                val selectusser:String = "op2"
                selecedOption = currentPosition+1
                op2.setBackgroundResource(R.drawable.selected_question_option)
                Checkaws(selectusser, op2)
            }
            R.id.op3 ->{
                val op3: TextView = findViewById(R.id.op3)
                val selectusser:String = "op3"
                selecedOption = currentPosition+1
                op3.setBackgroundResource(R.drawable.selected_question_option)
                Checkaws(selectusser, op3)
            }
            R.id.op4 ->{
                val op4: TextView = findViewById(R.id.op4)
                val selectusser:String = "op4"
                selecedOption = currentPosition+1
                op4.setBackgroundResource(R.drawable.selected_question_option)
                Checkaws(selectusser, op4)
            }
        }

    }
    private fun Checkaws(selectUser:String, op:TextView) {
        if(selecedOption!=0)
        {
            if (selectUser != aws){
                Handler().postDelayed({
                    op.setBackgroundResource(R.drawable.wrong_question_option)
                    showaws()
                    showDialog_wrog()
                }, 1000)
            }else{
                Handler().postDelayed({
                    op.setBackgroundResource(R.drawable.correct_question_option)
                    showDialog_corect()
                }, 1000)
            }
        }

        selecedOption=0
    }

    private fun showaws() {
        if (aws == "op1"){

                val op1: TextView = findViewById(R.id.op1)
                op1.setBackgroundResource(R.drawable.correct_question_option)

        }else if (aws == "op2")
        {

                val op2: TextView = findViewById(R.id.op2)
                op2.setBackgroundResource(R.drawable.correct_question_option)


        }
        else if (aws == "op3")
        {

                val op3: TextView = findViewById(R.id.op3)
                op3.setBackgroundResource(R.drawable.correct_question_option)


        }
        else if (aws == "op4")
        {

                val op4: TextView = findViewById(R.id.op4)
                op4.setBackgroundResource(R.drawable.correct_question_option)


        }

    }



    private fun showDialog_corect() {
        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.dialog_correct)
        val yesBtn = dialog.findViewById(R.id.yesbtn) as TextView
        dialog.show()
        music()
        yesBtn.setOnClickListener {
            music3()
            dialog.dismiss()
            getQSData()
            currentPosition++
            Toast.makeText(this, "Loading...", Toast.LENGTH_SHORT).show();
        }


    }
    private fun showDialog_wrog() {
        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.dialog_wrong)
        val yesBtn = dialog.findViewById(R.id.yesbtn) as TextView
        dialog.show()
        music2()
        yesBtn.setOnClickListener {
            dialog.dismiss()
            getQSData()
            currentPosition++
            Toast.makeText(this, "Loading...", Toast.LENGTH_SHORT).show();
        }
    }

    private fun music() {
        mediaPlayer = MediaPlayer.create(this, R.raw.ding)
        mediaPlayer.start()
    }
    private fun music2() {
        mediaPlayer = MediaPlayer.create(this, R.raw.wrong_buzzer)
        mediaPlayer.start()
    }
    private fun music3() {
        mediaPlayer = MediaPlayer.create(this, R.raw.applause_wav)
        mediaPlayer.start()
    }
    private fun showoptionlevel() {

            if (aws =="op1" && level.equals("1")){
                val oop1: TextView = findViewById(R.id.op1)
                val oop2: TextView = findViewById(R.id.op2)
                val oop3: TextView = findViewById(R.id.op3)
                val oop4: TextView = findViewById(R.id.op4)
                oop1.isVisible  = true
                oop2.isVisible  = true
                oop3.isVisible  = false
                oop4.isVisible  = false
            }
           if (aws =="op2" && level.equals("1"))
            {
                val oop1: TextView = findViewById(R.id.op1)
                val oop2: TextView = findViewById(R.id.op2)
                val oop3: TextView = findViewById(R.id.op3)
                val oop4: TextView = findViewById(R.id.op4)
                oop1.isVisible  = true
                oop2.isVisible  = true
                oop3.isVisible  = false
                oop4.isVisible  = false
            }
             if  (aws =="op3" && level.equals("1"))
            {
                val oop1: TextView = findViewById(R.id.op1)
                val oop2: TextView = findViewById(R.id.op2)
                val oop3: TextView = findViewById(R.id.op3)
                val oop4: TextView = findViewById(R.id.op4)
                oop1.isVisible  = false
                oop2.isVisible  = true
                oop3.isVisible  = true
                oop4.isVisible  = false
            }
           if  (aws == "op4" && level.equals("1"))
            {
                val oop1: TextView = findViewById(R.id.op1)
                val oop2: TextView = findViewById(R.id.op2)
                val oop3: TextView = findViewById(R.id.op3)
                val oop4: TextView = findViewById(R.id.op4)
                oop1.isVisible  = false
                oop2.isVisible  = false
                oop3.isVisible  = true
                oop4.isVisible  = true
            }


        ////////
            if  (aws =="op1" && level.equals("2")){
                val oop1: TextView = findViewById(R.id.op1)
                val oop2: TextView = findViewById(R.id.op2)
                val oop3: TextView = findViewById(R.id.op3)
                val oop4: TextView = findViewById(R.id.op4)
                oop1.isVisible  = true
                oop2.isVisible  = true
                oop3.isVisible  = true
                oop4.isVisible  = false
            }
           if (aws =="op2" && level.equals("2"))
            {
                val oop1: TextView = findViewById(R.id.op1)
                val oop2: TextView = findViewById(R.id.op2)
                val oop3: TextView = findViewById(R.id.op3)
                val oop4: TextView = findViewById(R.id.op4)
                oop1.isVisible  = true
                oop2.isVisible  = true
                oop3.isVisible  = true
                oop4.isVisible  = false
            }
            if (aws =="op3" && level.equals("2"))
             {
                val oop1: TextView = findViewById(R.id.op1)
                val oop2: TextView = findViewById(R.id.op2)
                val oop3: TextView = findViewById(R.id.op3)
                val oop4: TextView = findViewById(R.id.op4)
                oop1.isVisible  = false
                oop2.isVisible  = true
                oop3.isVisible  = true
                oop4.isVisible  = true
            }
           if (aws =="op4" && level.equals("2"))
            {
                val oop1: TextView = findViewById(R.id.op1)
                val oop2: TextView = findViewById(R.id.op2)
                val oop3: TextView = findViewById(R.id.op3)
                val oop4: TextView = findViewById(R.id.op4)
                oop1.isVisible  = true
                oop2.isVisible  = false
                oop3.isVisible  = true
                oop4.isVisible  = true
            }
//////

        if (level.equals("3"))
        {
            val oop1: TextView = findViewById(R.id.op1)
            val oop2: TextView = findViewById(R.id.op2)
            val oop3: TextView = findViewById(R.id.op3)
            val oop4: TextView = findViewById(R.id.op4)
            oop1.isVisible  = true
            oop2.isVisible  = true
            oop3.isVisible  = true
            oop4.isVisible  = true
        }


  }

}