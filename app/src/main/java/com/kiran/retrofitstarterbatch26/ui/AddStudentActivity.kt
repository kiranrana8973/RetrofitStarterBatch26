package com.kiran.retrofitstarterbatch26.ui

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.webkit.MimeTypeMap
import android.widget.*
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.contract.ActivityResultContracts
import com.google.android.material.textfield.TextInputEditText
import com.kiran.retrofitstarterbatch26.R
import com.kiran.retrofitstarterbatch26.ui.model.Student
import com.kiran.retrofitstarterbatch26.ui.repository.StudentRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*

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

    private val CAMERA_CODE = 1
    private val GALLERY_CODE = 0
    private var imageUrl = ""
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK) {
            //gallery
            if (requestCode == GALLERY_CODE && data != null) {
                val selectedImage = data.data
                val filePathColumn = arrayOf(MediaStore.Images.Media.DATA)
                val contentResolver = contentResolver
                val cursor =
                    contentResolver.query(selectedImage!!, filePathColumn, null, null, null)
                cursor!!.moveToFirst()
                val columnIndex = cursor.getColumnIndex(filePathColumn[0])
                imageUrl = cursor.getString(columnIndex)
                imgProfile.setImageBitmap(BitmapFactory.decodeFile(imageUrl))
                cursor.close()
            } else if (requestCode == CAMERA_CODE && data != null) {
                val imageBitmap = data.extras?.get("data") as Bitmap
                val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
                val file = bitmapToFile(imageBitmap, "$timeStamp.jpg")
                imageUrl = file!!.absolutePath
                imgProfile.setImageBitmap(BitmapFactory.decodeFile(imageUrl))
            }
        }
    }

    private fun bitmapToFile(
        bitmap: Bitmap,
        fileNameToSave: String
    ): File? {
        var file: File? = null
        return try {
            file = File(
                getExternalFilesDir(Environment.DIRECTORY_PICTURES)
                    .toString() + File.separator + fileNameToSave
            )
            file.createNewFile()

            //Convert bitmap to byte array
            val bos = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, bos) // YOU can also save it in JPEG
            val bitMapData = bos.toByteArray()
            //write the bytes in file
            val fos = FileOutputStream(file)
            fos.write(bitMapData)
            fos.flush()
            fos.close()
            file

        } catch (e: java.lang.Exception) {
            e.printStackTrace()
            file // it will return null
        }
    }


    private fun saveStudent() {
        val fullName = etFullName.text.toString()
        val age = etAge.text.toString().toInt()
        val address = etAddress.text.toString()
        var gender = ""
        when {
            rdoFemale.isChecked -> {
                gender = "Female"
            }
            rdoMale.isChecked -> {
                gender = "Male"
            }
            rdoOthers.isChecked -> {
                gender = "Others"
            }
        }
        val student =
            Student(
                fullname = fullName, age = age,
                gender = gender, address = address
            )
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val studentRepository = StudentRepository()
                val response = studentRepository.insertStudent(student)
                if (response.success == true) {
                    if (imageUrl != "") {
                        uploadImage(response.data!!._id)
                    }
                    withContext(Dispatchers.Main) {
                        Toast.makeText(
                            this@AddStudentActivity,
                            "Student Added",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            } catch (ex: Exception) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(
                        this@AddStudentActivity,
                        ex.toString(),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }

    }

    private fun uploadImage(id: String) {

        val file = File(imageUrl)
        val mimeType = getMimeType(file)

        val reqFile = RequestBody.create(MediaType.parse(mimeType), file)

        val body = MultipartBody.Part.createFormData("file", file.name, reqFile)

        CoroutineScope(Dispatchers.IO).launch {
            try {
                val studentRepository = StudentRepository()
                val response = studentRepository.uploadImage(id, body)
                if (response.success == true) {
                    withContext(Dispatchers.Main) {
                        Toast.makeText(
                            this@AddStudentActivity,
                            "Upload bhayo hai",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            } catch (ex: Exception) {
                withContext(Dispatchers.Main) {
                    Log.d("Mero Error ", ex.localizedMessage)
                    Toast.makeText(
                        this@AddStudentActivity,
                        ex.localizedMessage,
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    fun getMimeType(file: File): String? {
        var type: String? = null
        val extension = MimeTypeMap.getFileExtensionFromUrl(file.path)
        if (extension != null) {
            type = MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension)
        }
        return type
    }

}


