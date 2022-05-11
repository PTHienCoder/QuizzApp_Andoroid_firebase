package com.example.appquizgame_cki

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.internal.ContextUtils.getActivity
import com.google.firebase.database.*

class ViewAll_QS : AppCompatActivity() {
    private lateinit var dbref : DatabaseReference
    var cate : String ? =null
    private lateinit var QSRecyclerview : RecyclerView

    private lateinit var QSArrayList : ArrayList<model_AddQS>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_all_qs)

        cate =intent.getStringExtra("Category")
        QSRecyclerview = findViewById(R.id.rcv)
        QSArrayList = arrayListOf<model_AddQS>()
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
                        val QS = jh.getValue(model_AddQS::class.java)
                        val id = jh.child("type").getValue().toString()
                        if(id == cate){
                            QSArrayList.add(QS!!)
                        }
                        val layoutManager = LinearLayoutManager(applicationContext);
                        QSRecyclerview.setLayoutManager(layoutManager);
                        val adapter : Adapter_QS = Adapter_QS(applicationContext ,QSArrayList)
                        layoutManager.setStackFromEnd(true)
                        QSRecyclerview.setAdapter(adapter)
                    }


                }

            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }


        })

    }
    override fun onBackPressed() {
        super.onBackPressed()
    }
}