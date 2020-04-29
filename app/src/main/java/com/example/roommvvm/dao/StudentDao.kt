package com.example.roommvvm.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.roommvvm.entity.Student

//mirip sebagai class controller Laravel
@Dao
interface StudentDao {
    //fungsi DAO untuk ambil semua data dari table student
    @Query("Select * from student")
    fun getAll(): List<Student>

    //fungsi DAO untuk insert data ke table student
    @Insert
    fun insertStudent(item: Student)
}