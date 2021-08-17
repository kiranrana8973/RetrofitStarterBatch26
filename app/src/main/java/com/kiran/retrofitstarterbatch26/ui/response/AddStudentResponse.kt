package com.kiran.retrofitstarterbatch26.ui.response

import com.kiran.retrofitstarterbatch26.ui.model.Student

data class AddStudentResponse (
    val success : Boolean? = null,
    val data : Student? = null
        )