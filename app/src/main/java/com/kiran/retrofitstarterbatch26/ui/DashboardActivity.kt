package com.kiran.retrofitstarterbatch26.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.AppCompatImageButton
import com.kiran.retrofitstarterbatch26.R

class DashboardActivity : AppCompatActivity() {
    private lateinit var btnAddStudent : AppCompatImageButton
    private lateinit var btnViewStudent : AppCompatImageButton
    private lateinit var btnGoogleMap : AppCompatImageButton
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        btnAddStudent = findViewById(R.id.btnAddStudent)
        btnViewStudent = findViewById(R.id.btnViewStudent)
        btnGoogleMap = findViewById(R.id.btnGoogleMap)
        btnViewStudent.setOnClickListener {
            startActivity(Intent(this@DashboardActivity,ViewStudentActivity::class.java))
        }
        btnAddStudent.setOnClickListener {
            startActivity(Intent(this@DashboardActivity,AddStudentActivity::class.java))
        }
    }
}