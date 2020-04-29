package com.example.roommvvm.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

//mirip sebagai class model Laravel
@Entity
data class Student (
    //inisialisasi Primary Key
    @PrimaryKey(autoGenerate = true) var id: Int? = null,

    //inisialisasi kolom yang ada di table
    @ColumnInfo var name: String = ""
)