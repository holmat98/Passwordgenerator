package com.example.passwordgenerator.View

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import com.example.passwordgenerator.Model.HelperClass
import com.example.passwordgenerator.R
import com.example.passwordgenerator.ViewModel.PasswordViewModel
import kotlinx.android.synthetic.main.fragment_passwd_generator.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [PasswdGeneratorFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class PasswdGeneratorFragment : Fragment(), AdapterView.OnItemSelectedListener {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private var passwordLengths: List<Int> = listOf( 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20 )
    private var length: Int = 8
    private var createdPassword: String = ""

    private lateinit var adapter: ArrayAdapter<Int>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment

        adapter = context?.let {
            ArrayAdapter(
                    it,
                    android.R.layout.simple_spinner_item,
                    passwordLengths
            )
        }!!
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        return inflater.inflate(R.layout.fragment_passwd_generator, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        passwordLengthSpinner.adapter = adapter
        passwordLengthSpinner.onItemSelectedListener = this

        createPasswordButton.setOnClickListener {
            passwordLinearLayout.isVisible = true
            createdPassword = HelperClass.generatePassword(isWithLetters = true, isWithUpperCase = true, isWithNumbers = true, isWithSpecial = true, length =  length)
            passwordTextView.text = createdPassword
        }

        openDialogBtn.setOnClickListener{
            var password: String = passwordTextView.text.toString()

            HelperClass.password = password
            var dialog = AddPasswordDialog()
            dialog.show(requireActivity().supportFragmentManager, "customDialog")
        }

    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment PasswdGeneratorFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
                PasswdGeneratorFragment().apply {
                    arguments = Bundle().apply {
                        putString(ARG_PARAM1, param1)
                        putString(ARG_PARAM2, param2)
                    }
                }
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        length = parent?.getItemAtPosition(position).toString().toInt()
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        length = 8
    }
}