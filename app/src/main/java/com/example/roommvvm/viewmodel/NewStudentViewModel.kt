package com.example.roommvvm.viewmodel

import android.app.Application
import android.graphics.Movie
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.roommvvm.data.AppDatabase
import com.example.roommvvm.entity.Student
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import timber.log.Timber

//class ini guna merapikan source code pada class activity(fragment)
class NewStudentViewModel(application: Application) : AndroidViewModel(application) {
    //inisialisasi penggunaan AppDatabase
    private val mDb: AppDatabase? = AppDatabase.getInstance(application)
    //inisialisasi List untuk data student
    private val allStudent = MutableLiveData<List<Student>>()

    //fun guna untuk store data ke table
    fun storeStudent(title: String) {
        //inisialisasi penggunaan entity Student
        val student = Student()
        //inisialisasi column
        student.name = title
        GlobalScope.launch {
            //akses fun Dao untuk insert data
            mDb?.studentDao()?.insertStudent(student)
        }
    }

    fun retrieveStudent(): LiveData<List<Student>> {
        GlobalScope.launch {
            //akses fun Dao untuk ambil data
            val list = mDb?.studentDao()?.getAll()
            //menampilkan log jumlah data
            Timber.i("Data yang ada sejumlah {${list?.size}}")
            //isi value dengan data baru yang diambil dari database
            allStudent.postValue(list)
        }
        //mengembalikan data dari list allStudent
        return allStudent
    }
}