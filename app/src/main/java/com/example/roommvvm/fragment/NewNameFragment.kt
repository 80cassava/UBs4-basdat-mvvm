package com.example.roommvvm.fragment

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProviders

import com.example.roommvvm.R
import com.example.roommvvm.viewmodel.NewStudentViewModel
import kotlinx.android.synthetic.main.fragment_new_name.*

/**
 * A simple [Fragment] subclass.
 */
class NewNameFragment : Fragment() {

    //listener untuk kegiatan di Fragment
    private var listener: OnFragmentInteractionListener? = null
    //inisialisasi NewStudentViewModel
    private lateinit var mViewModel: NewStudentViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //memanggil class NewStudentViewModel sebagai fragment
        mViewModel = ViewModelProviders.of(this).get(NewStudentViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_new_name, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //yang dilakukan ketika tombol add di tekan
        button.setOnClickListener {
            //ambil data dari editText
            val input = editText.text.toString().trim()
            //cek jika inputan kosong
            if (input.isEmpty()) {
                Toast.makeText(activity, "Nama dibutuhkan", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            //cek jika inputan terlalu panjang
            if (input.length > 30) {
                Toast.makeText(activity, "Nama terlalu panjang", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            //memanggil fun storeStudent beserta dengan input data
            mViewModel.storeStudent(input)
            //output notif data tersimpan
            Toast.makeText(activity, "$input entered", Toast.LENGTH_SHORT).show()
            //kembali ke fragment List
            listener?.goToStudentListFragment()
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
        fun goToStudentListFragment()
    }

    companion object {
        //generate static method
        @JvmStatic
        fun newInstance() = NewNameFragment()
    }
}
