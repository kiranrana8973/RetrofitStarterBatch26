package com.kiran.retrofitstarterbatch26.ui.response

import com.kiran.retrofitstarterbatch26.ui.model.Student

data class StudentGetResponse (
    val success: Boolean? = null,
    val data: MutableList<Student>? =null,

)