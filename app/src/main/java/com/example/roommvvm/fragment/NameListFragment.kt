package com.example.roommvvm.fragment

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager

import com.example.roommvvm.R
import com.example.roommvvm.data.AppDatabase
import com.example.roommvvm.helper.StudentRecyclerAdapter
import com.example.roommvvm.viewmodel.NewStudentViewModel
import kotlinx.android.synthetic.main.fragment_name_list.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import timber.log.Timber

/**
 * A simple [Fragment] subclass.
 */
class NameListFragment : Fragment() {

    private var listener: OnFragmentInteractionListener? = null
    private lateinit var mViewModel: NewStudentViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //Memanggil NewStudentViewModel sebagai fragment
        mViewModel = ViewModelProviders.of(this).get(NewStudentViewModel::class.java)
        //Menjalankan fun retriveStudent()/Mengamil data dari database
        mViewModel.retrieveStudent().observe(this, Observer {
            //Menampilkan size list data di log
            Timber.i("menerima perubahan data ${it.size}")
            //Menampilkan list ke Recycler menggunakan Adapter
            rvList.adapter = StudentRecyclerAdapter(it)
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_name_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //
        rvList.layoutManager = LinearLayoutManager(activity)
        //ketika button Add di click
        btnAdd.setOnClickListener {
            //memanggil fun DAO untuk akses ke database
            val dao = AppDatabase.getInstance(this.context!!)?.studentDao()
            GlobalScope.launch {
                //memanggil fun getAll() dari studentDao()
                dao?.getAll()
            }
            //Menjalankan fun goToNewNameFragment()
            listener?.goToNewNameFragment()
        }
    }

    //fun ketika fragment dibuka
    override fun onAttach(context: Context) {
        super.onAttach(context)
        //cek apakah context adalah interface
        if (context is OnFragmentInteractionListener) {
            listener = context
        } else {
            throw RuntimeException(context.toString() + " must implement OnFragmentInteractionListener")
        }
    }

    //fun ketika fragment ditutup
    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    interface OnFragmentInteractionListener {
        fun goToNewNameFragment()
    }

    companion object {
        //generate static method
        @JvmStatic
        fun newInstance() = NameListFragment()
    }

}
