package com.example.passwordgenerator.View

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import androidx.core.view.marginEnd
import androidx.core.view.marginStart
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.passwordgenerator.Model.SwipeToDeleteCallback
import com.example.passwordgenerator.R
import com.example.passwordgenerator.ViewModel.PasswordViewModel
import com.example.passwordgenerator.ViewModel.PasswordsAdapter
import kotlinx.android.synthetic.main.fragment_passwords.*


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [PasswordsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class PasswordsFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private lateinit var myAdapter: PasswordsAdapter
    private lateinit var myLayoutManager: LinearLayoutManager
    private lateinit var recyclerView: RecyclerView

    private lateinit var viewModel: PasswordViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        viewModel = ViewModelProvider(requireActivity()).get(PasswordViewModel::class.java)

        myLayoutManager = LinearLayoutManager(context)
        myAdapter = PasswordsAdapter(viewModel.passwords, requireActivity().supportFragmentManager, viewModel, context)

        viewModel.passwords.observe(viewLifecycleOwner, Observer {
            myAdapter.notifyDataSetChanged()
        })

        return inflater.inflate(R.layout.fragment_passwords, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val sharedPref = activity?.getSharedPreferences("Password", Context.MODE_PRIVATE)
        val editor = sharedPref?.edit()

        val inputET = EditText(context).apply {
            hint = "Enter password"
            inputType = InputType.TYPE_TEXT_VARIATION_PASSWORD
        }

        val passwordDialog = AlertDialog.Builder(requireContext()).apply {
            setView(inputET)
            setTitle("Enter the password")
            setPositiveButton("YES"){dialog, _ ->
                if(inputET.text.toString().equals(sharedPref?.getString("password", ""))){
                    passwordsRV.isVisible = true
                    addPasswordFloatingBtn.isVisible = true
                }
                else{
                    Toast.makeText(context, "Wrong password", Toast.LENGTH_SHORT).show()
                }
                dialog.dismiss()
            }
            setNegativeButton("NO"){dialog, _ -> dialog.dismiss()}
        }

        val createPasswordDialog = AlertDialog.Builder(requireContext()).apply {
            setView(inputET)
            setTitle("Create password")
            setPositiveButton("YES"){dialog, _ ->
                val passwd: String = inputET.text.toString()
                if(passwd.isNotEmpty()){
                    editor?.apply {
                        putString("password", passwd)
                        apply()
                    }
                    passwordsRV.isVisible = true
                    addPasswordFloatingBtn.isVisible = true
                }
                dialog.dismiss()
            }
            setNegativeButton("NO"){dialog, _ -> dialog.dismiss()}

        }

        if(sharedPref?.getString("password", "").isNullOrEmpty()){
            createPasswordDialog.show()
        }
        else{
            passwordDialog.show()
        }

        recyclerView = passwordsRV.apply {
            this.adapter = myAdapter
            this.layoutManager = myLayoutManager
        }

        val swipeHandler = object : SwipeToDeleteCallback(context){
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val adapter = recyclerView.adapter as PasswordsAdapter
                adapter.removeAt(viewHolder.adapterPosition)
            }
        }

        addPasswordFloatingBtn.setOnClickListener {
            val i = Intent(context, AddPasswordActivity::class.java)

            requireActivity().startActivity(i)
        }

        val itemTouchHelper = ItemTouchHelper(swipeHandler)
        itemTouchHelper.attachToRecyclerView(recyclerView)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment PasswordsFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            PasswordsFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}