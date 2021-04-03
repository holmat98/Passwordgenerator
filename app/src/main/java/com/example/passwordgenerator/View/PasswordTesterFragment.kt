package com.example.passwordgenerator.View

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.passwordgenerator.Model.HelperClass
import com.example.passwordgenerator.R
import kotlinx.android.synthetic.main.fragment_password_tester.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [PasswordTesterFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class PasswordTesterFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

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
        return inflater.inflate(R.layout.fragment_password_tester, container, false)
    }

    @SuppressLint("ResourceAsColor")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        passwordTesterButton.setOnClickListener {
            var password: String = passwordToTest.getText().toString()
            if(!"".equals(password)){
                val score: Double = HelperClass.testPassword(password)
                when(score){
                    in 0.00..0.10 -> {
                        passwordTestScore.text = "VERY WEAK"
                        passwordTesterLayout.setBackgroundColor(Color.parseColor("#F50C05"))
                    }
                    in 0.10..0.30 -> {
                        passwordTestScore.text = "WEAK"
                        passwordTesterLayout.setBackgroundColor(Color.parseColor("#DB7216"))
                    }
                    in 0.30..0.70 -> {
                        passwordTestScore.text = "NEUTRAL"
                        passwordTesterLayout.setBackgroundColor(Color.parseColor("#F5D10D"))
                    }
                    in 0.70..0.90 -> {
                        passwordTestScore.text = "STRONG"
                        passwordTesterLayout.setBackgroundColor(Color.parseColor("#BBEB0C"))
                    }
                    in 0.90..1.00 -> {
                        passwordTestScore.text = "VERY STRONG"
                        passwordTesterLayout.setBackgroundColor(Color.parseColor("#13EB48"))
                    }
                }


            }
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment PasswordTesterFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
                PasswordTesterFragment().apply {
                    arguments = Bundle().apply {
                        putString(ARG_PARAM1, param1)
                        putString(ARG_PARAM2, param2)
                    }
                }
    }
}