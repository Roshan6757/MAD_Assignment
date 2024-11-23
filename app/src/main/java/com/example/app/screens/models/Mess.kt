package com.example.app.screens.models

data class Mess(
    val name: String,
    val lunchStartTime: String,
    val lunchEndTime: String,
    val dinnerStartTime: String,
    val dinnerEndTime: String,
    val registeredStudents: MutableList<Student> = mutableListOf()
)