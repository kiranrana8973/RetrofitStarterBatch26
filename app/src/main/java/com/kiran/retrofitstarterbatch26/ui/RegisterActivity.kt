package com.kiran.retrofitstarterbatch26.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.kiran.retrofitstarterbatch26.R
import com.kiran.retrofitstarterbatch26.ui.model.User
import com.kiran.retrofitstarterbatch26.ui.repository.UserRepository
import com.kiran.retrofitstarterbatch26.ui.response.UserResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.Exception

class RegisterActivity : AppCompatActivity() {
    private lateinit var etFname: EditText
    private lateinit var etLname: EditText
    private lateinit var etUsername: EditText
    private lateinit var etPassword: EditText
    private lateinit var etConfirmPassword: EditText
    private lateinit var btnAddUser: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        etFname = findViewById(R.id.etFname)
        etLname = findViewById(R.id.etLname)
        etUsername = findViewById(R.id.etUsername)
        etPassword = findViewById(R.id.etPassword)
        etConfirmPassword = findViewById(R.id.etConfirmPassword)
        btnAddUser = findViewById(R.id.btnAddUser)

        btnAddUser.setOnClickListener {

            val fname = etFname.text.toString()
            val lname = etLname.text.toString()
            val username = etUsername.text.toString()
            val password = etPassword.text.toString()
            val confirmPassword = etConfirmPassword.text.toString()

            if (password != confirmPassword) {
                etPassword.error = "Password does not match"
                etPassword.requestFocus()
                return@setOnClickListener
            } else {
                val user =
                    User(fname = fname, lname = lname, username = username, password = password)

                CoroutineScope(Dispatchers.IO).launch {
                    try {
                        val repository = UserRepository()
                        val response = repository.registerUser(user)
                        if(response.success == true){
                            withContext(Main){
                                Toast.makeText(this@RegisterActivity, "Successfully registered", Toast.LENGTH_SHORT).show()
                            }
                        }
                    } catch (ex: Exception) {
                        withContext(Main) {
                            Toast.makeText(this@RegisterActivity, ex.toString(), Toast.LENGTH_SHORT)
                                .show()
                        }
                    }
                }

            }
        }
    }
}