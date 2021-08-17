package com.kiran.retrofitstarterbatch26.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.*
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.contract.ActivityResultContracts
import com.google.android.material.textfield.TextInputEditText
import com.kiran.retrofitstarterbatch26.R

class AddStudentActivity : AppCompatActivity() {
    private lateinit var etFullName: TextInputEditText
    private lateinit var etAge: TextInputEditText
    private lateinit var etAddress: TextInputEditText
    private lateinit var rdoMale: RadioButton
    private lateinit var rdoFemale: RadioButton
    private lateinit var rdoOthers: RadioButton
    private lateinit var btnSave: Button
    private lateinit var imgProfile: ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_student)


        etFullName = findViewById(R.id.etFullName)
        etAge = findViewById(R.id.etAge)
        etAddress = findViewById(R.id.etAddress)
        rdoMale = findViewById(R.id.rdoMale)
        rdoFemale = findViewById(R.id.rdoFemale)
        rdoOthers = findViewById(R.id.rdoOthers)
        btnSave = findViewById(R.id.btnSave)
        imgProfile = findViewById(R.id.imgProfile)

        btnSave.setOnClickListener {
            saveStudent()
        }

        imgProfile.setOnClickListener {
            loadPopUpMenu()
        }
    }

    private fun loadPopUpMenu() {
        val popMenu = PopupMenu(this@AddStudentActivity, imgProfile)
        popMenu.menuInflater.inflate(R.menu.gallery_camera, popMenu.menu)
        popMenu.setOnMenuItemClickListener { item ->
            if (item.itemId == R.id.menuCamera) {
                openCamera()
            } else if (item.itemId == R.id.menuGallery) {
//                openGallery.launch("image/*")
                openGallery()
            }
            true
        }
        popMenu.show()

    }

    private val CAMERA_CODE = 1
    private val GALLERY_CODE = 0

    private fun openGallery() {
        val galleryOpenGaraHai = Intent(Intent.ACTION_PICK)
        galleryOpenGaraHai.type = "image/*"
        startActivityForResult(galleryOpenGaraHai, GALLERY_CODE)
    }

//    private val openGallery = registerForActivityResult(ActivityResultContracts.GetContent(),
//        ActivityResultCallback { imageUrl ->
//            imgProfile.setImageURI(imageUrl)
//        })

    private fun openCamera() {
        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(cameraIntent, CAMERA_CODE)

    }

    private fun saveStudent() {

    }
}