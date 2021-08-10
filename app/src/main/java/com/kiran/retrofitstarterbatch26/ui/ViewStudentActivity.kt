package com.kiran.retrofitstarterbatch26.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.kiran.retrofitstarterbatch26.R
import com.kiran.retrofitstarterbatch26.ui.repository.StudentRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.Exception

class ViewStudentActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_student)
        getAllStudents()
    }

    private fun getAllStudents() {
        CoroutineScope(Dispatchers.IO).launch {
            try{
                val repository = StudentRepository()
                val response = repository.getAllStudent()
                if(response.success==true){
                    val student = response.data
                    withContext(Main){
                        Toast.makeText(this@ViewStudentActivity, student!!.size.toString(), Toast.LENGTH_SHORT).show()
                    }
                }
            }catch(ex : Exception){
                withContext(Main){
                    Toast.makeText(this@ViewStudentActivity, ex.toString(), Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}