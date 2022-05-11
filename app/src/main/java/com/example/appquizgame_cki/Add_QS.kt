package com.example.appquizgame_cki

import android.Manifest
import android.app.ProgressDialog
import android.content.ActivityNotFoundException
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.text.TextUtils
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.view.isVisible
import com.google.android.gms.tasks.Task
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import java.io.File


class Add_QS : AppCompatActivity() {
    private lateinit var imageView: ImageView
    private lateinit var file: File
    private var ass: String = "null"
    private lateinit var uri : Uri
    private lateinit var camIntent:Intent
    private lateinit var galIntent:Intent
    private lateinit var cropIntent: Intent
    private lateinit var btnImg: TextView
    private lateinit var qs: TextView
    private lateinit var op1: EditText
    private lateinit var op2: EditText
    private lateinit var op3: EditText
    private lateinit var op4: EditText
    private lateinit var btnsubmit: TextView
    private lateinit var dbref: DatabaseReference
    private lateinit var storagefirebase: StorageReference
    private val paths = arrayOf("Đáp án 1", "Đáp án 2", "Đáp án 3", "Đáp án 4")
    private val paths2 = arrayOf("Đồ vật & màu sắc", "Động vật", "Vật dụng phương tiện", "Đồ ăn")
    private lateinit var progressDialog: ProgressDialog
    private lateinit var aws:String
    private lateinit var type: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_qs)
       val spinner = findViewById<Spinner>(R.id.spinner1)
        val adapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_item, paths
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
//        spinner.setOnItemSelectedListener(this);
        progressDialog = ProgressDialog(this);

        val spinner2 = findViewById<Spinner>(R.id.spinner2)
        val adapter2 = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_item, paths2
        )
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner2.setAdapter(adapter2);
//        spinner.setOnItemSelectedListener(this);



         qs= findViewById(R.id.qs)
         op1 = findViewById(R.id.da1)
         op2  = findViewById(R.id.da2)
         op3  = findViewById(R.id.da3)
         op4 = findViewById(R.id.da4)
        btnsubmit = findViewById(R.id.submit)

        imageView = findViewById(R.id.image_qs)
        btnImg = findViewById(R.id.imgtv)
        enableRuntimePermission()
        imageView.isVisible = false
        imageView.setOnClickListener { openDialog() }
        btnImg.setOnClickListener { openDialog() }

        btnsubmit.setOnClickListener{AddQS()}
      val btnback : Button = findViewById<Button?>(R.id. action_menu_presenter)
        btnback.setOnClickListener{
          onBackPressed()
      }


    }


    private fun AddQS() {
        val qss = qs.text.toString()
        val op11 = op1.text.toString()
        val op22 = op2.text.toString()
        val op33 = op3.text.toString()
        val op44 = op4.text.toString()
        if (TextUtils.isEmpty(qss) || TextUtils.isEmpty(op11) || TextUtils.isEmpty(op22)
            || TextUtils.isEmpty(op33) || TextUtils.isEmpty(op44)){
            Toast.makeText(this, " Vui lòng điền đầy đủ, Không được để trống câu hỏi hoặc đáp án nào !", Toast.LENGTH_SHORT).show();
            return;
        }

        progressDialog.setTitle("Please Wait")
        progressDialog.setMessage("Loading ...")
        progressDialog.show()

        if(imageView.drawable != null){
            val random1 = (0..1000).shuffled().last()
            val filePathAndName = "Posts/" + "post_" +random1

            storagefirebase = FirebaseStorage.getInstance().getReference().child(filePathAndName)
            storagefirebase.putFile(uri).addOnSuccessListener {
                val uriTask: Task<Uri> = it.getStorage().getDownloadUrl()
                while (!uriTask.isSuccessful());
                val dowloadUri = uriTask.getResult().toString();
                if (uriTask.isSuccessful()){
                    uploaddata(dowloadUri)
                }
            }.addOnFailureListener{
                Toast.makeText(this,
                    "Xin lỗi, Thêm câu hỏi thất bại",
                    Toast.LENGTH_SHORT).show()
                progressDialog.dismiss()
            }

        }else{
            val dowloadUri:String = "NoImage";
                uploaddata(dowloadUri)
        }

    }
    private fun uploaddata( dowloadUri:String) {


        Changedata1()
        Changedata2()
        val random1 = (0..1000).shuffled().last()
        val qsss = qs.text.toString()
        val op11 = op1.text.toString()
        val op22 = op2.text.toString()
        val op33 = op3.text.toString()
        val op44 = op4.text.toString()
//        val result: HashMap<String, Any> = HashMap()
//        result["image"] = dowloadUr
        val listQS = model_AddQS(random1.toString(), qsss,dowloadUri,type,op11,op22,op33,op44,aws)
        val sss = random1.toString()
        dbref = FirebaseDatabase.getInstance().getReference("Question")
        if(sss != null){
            dbref.child(sss).setValue(listQS).addOnCompleteListener{
                if(it.isSuccessful){
                    Toast.makeText(this,
                        "Thêm câu hỏi thành công",
                        Toast.LENGTH_SHORT).show()
                    progressDialog.dismiss()
                    qs.text = null
                    op1.text = null
                    op2.text = null
                    op3.text = null
                    op4.text = null
                    onBackPressed()

                }else{
                    Toast.makeText(this,
                        "Xin lỗi, Thêm câu hỏi thất bại",
                        Toast.LENGTH_SHORT).show()
                    progressDialog.dismiss()
                }
            }
        }

    }
    private fun Changedata1() {
        val spinner = findViewById<Spinner>(R.id.spinner1)
        val ds = spinner.selectedItem.toString()
        if (ds.equals("Đáp án 1")){
            aws = "op1"
        }else if (ds.equals("Đáp án 2")){
            aws = "op2"
        }else if (ds.equals("Đáp án 3")){
            aws = "op3"
        }else if (ds.equals("Đáp án 4")){
            aws = "op4"
        }
    }
    private fun Changedata2() {
        val spinner = findViewById<Spinner>(R.id.spinner2)
        val ds = spinner.selectedItem.toString()
        if (ds.equals("Đồ vật & màu sắc")){
            type = "a"
        }else if (ds.equals("Động vật")){
            type = "b"
        }else if (ds.equals("Vật dụng phương tiện")){
            type = "c"
        }else if (ds.equals("Đồ ăn")){
            type = "d"
        }
    }




    private fun openDialog() {
        val openDialog = AlertDialog.Builder(this)
        openDialog.setIcon(R.drawable.ic_menu_icon)
        openDialog.setTitle("Choose your Image in...!!")
        openDialog.setPositiveButton("Camera"){
                dialog,_->
            openCamera()
            dialog.dismiss()

        }
        openDialog.setNegativeButton("Gallery"){
                dialog,_->
            openGallery()
            dialog.dismiss()
        }
        openDialog.setNeutralButton("Cancel"){
                dialog,_->
            dialog.dismiss()
        }
        openDialog.create()
        openDialog.show()

    }

    private fun openGallery() {
        galIntent = Intent(Intent.ACTION_PICK,
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        )
        startActivityForResult(Intent.createChooser(galIntent,
            "Select Image From Gallery "),2)
    }

    private fun openCamera() {
        camIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        file = File(
            Environment.getExternalStorageDirectory(),
            "file"+System.currentTimeMillis().toString()+".jpg"
        )
        uri = Uri.fromFile(file)
        camIntent.putExtra(MediaStore.EXTRA_OUTPUT,uri)
        camIntent.putExtra("return-data",true)
        startActivityForResult(camIntent,0)
    }

    private fun enableRuntimePermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(
                this,Manifest.permission.CAMERA
            )){
            Toast.makeText(this,
                "Camera Permission allows us to Camera App",
                Toast.LENGTH_SHORT).show()
        }
        else{
            ActivityCompat.requestPermissions(this,
                arrayOf(Manifest.permission.CAMERA),RequestPermissionCode)
        }
    }

    private fun cropImages(){
        /**set crop image*/
        try {
            cropIntent = Intent("com.android.camera.action.CROP")
            cropIntent.setDataAndType(uri,"image/*")
            cropIntent.putExtra("crop",true)
            cropIntent.putExtra("outputX",180)
            cropIntent.putExtra("outputY",180)
            cropIntent.putExtra("aspectX",3)
            cropIntent.putExtra("aspectY",2)
            cropIntent.putExtra("scaleUpIfNeeded",true)
            cropIntent.putExtra("return-data",true)
            startActivityForResult(cropIntent,1)

        }catch (e: ActivityNotFoundException){
            e.printStackTrace()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 0 && resultCode == RESULT_OK){
            cropImages()
        } else if (requestCode == 2){
            if (data != null){
                imageView.isVisible = true
                uri = data.data!!
                cropImages()
            }
        }
        else if (requestCode == 1){
            if (data != null){
                imageView.isVisible = true
                val bundle = data.extras
                val bitmap = bundle!!.getParcelable<Bitmap>("data")
                imageView.setImageBitmap(bitmap)
            }
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            RequestPermissionCode-> if (grantResults.size>0
                && grantResults[0]== PackageManager.PERMISSION_GRANTED){
                Toast.makeText(this,
                    "Permission Granted , Now your application can access Camera",
                    Toast.LENGTH_SHORT).show()
            }
            else{
                Toast.makeText(this,
                    "Permission Granted , Now your application can not  access Camera",
                    Toast.LENGTH_SHORT).show()
            }
        }
    }


    companion object{
        const val RequestPermissionCode = 111
    }


    override fun onBackPressed() {
        super.onBackPressed()
    }
}



