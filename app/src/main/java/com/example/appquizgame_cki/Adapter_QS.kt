package com.example.appquizgame_cki

import android.app.AlertDialog
import android.app.ProgressDialog
import android.content.Context
import android.support.annotation.NonNull
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.squareup.picasso.Picasso


class Adapter_QS( val context: Context,  val qslist : ArrayList<model_AddQS>) : RecyclerView.Adapter<Adapter_QS.MyViewHolder>()
{
    private lateinit var progressDialog: ProgressDialog
    private lateinit var dbref: DatabaseReference
    private lateinit var storagefirebase: StorageReference
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.iteam_qs,
            parent,false)
        return MyViewHolder(itemView)

    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        val currentitem = qslist[position]
//
        holder.Qs_content.text = currentitem.qs + " ?"
//        Picasso.get().load(currentitem.image).into(holder.Image_QS)
        if(currentitem.image.equals("NoImage")){
            Picasso.get().load(R.drawable.ic_qs_noimage).into(holder.Image_QS)
        }else{
            Picasso.get().load(currentitem.image).into(holder.Image_QS)
        }
//        Glide
//            .with(context)
//            .load(currentitem.image)
//            .centerCrop()
//            .into(holder.Image_QS)

        holder.btnxoa.setOnClickListener{


                if(currentitem.image.equals("NoImage")){
                    xoadata(currentitem.Id.toString())
                }else{
                    val image = currentitem.image.toString()
                    storagefirebase  = FirebaseStorage.getInstance().getReferenceFromUrl(image)
                    storagefirebase.delete().addOnSuccessListener {
                        xoadata(currentitem.Id.toString())
//                    progressDialog.dismiss()
                        Toast.makeText(context, "Xoá thành công.", Toast.LENGTH_SHORT).show()

                    }.addOnFailureListener{
                        Toast.makeText(context, "Xoá thất bại.", Toast.LENGTH_SHORT).show()
//                    progressDialog.dismiss()
                    }
                }





    }

    }
    override fun getItemCount(): Int {
        return qslist.size
    }

    private fun xoadata( Id : String) {
        dbref = FirebaseDatabase.getInstance().getReference("Question").child(Id)
        dbref.removeValue().addOnSuccessListener{
             Toast.makeText(context, "Xoá thành công.", Toast.LENGTH_SHORT).show()

        }.addOnFailureListener{
                 Toast.makeText(context, "Xoá thất bại.", Toast.LENGTH_SHORT).show()
////                            progressDialog.dismiss()
        }
//        dbref.addListenerForSingleValueEvent(object : ValueEventListener {
//            override fun onDataChange(@NonNull snapshot: DataSnapshot) {
//                for (ds in snapshot.children) {
//                    val id = ds.child("Id").getValue().toString()
//                    if(id == Id){
//                        ds.ref.removeValue()
//                    }
//
//                }
//                Toast.makeText(context, "Xoá thành công.", Toast.LENGTH_SHORT).show()
////                            progressDialog.dismiss()
//            }
//
//            override fun onCancelled(@NonNull error: DatabaseError) {
//                Toast.makeText(context, "Xoá thất bại.", Toast.LENGTH_SHORT).show()
////                            progressDialog.dismiss()
//            }
//        })
    }

    private fun openDialog( ) {


    }
    class MyViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){

        val Qs_content : TextView = itemView.findViewById(R.id.qsiteam)
        val Image_QS : ImageView = itemView.findViewById(R.id.imageqss)
        val btnxoa : ImageView = itemView.findViewById(R.id.btn_del)
    }

}