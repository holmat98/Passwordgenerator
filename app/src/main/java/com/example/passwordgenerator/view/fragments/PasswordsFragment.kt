package com.example.passwordgenerator.view.fragments

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.passwordgenerator.R
import com.example.passwordgenerator.model.Cryptography

class PasswordsFragment : Fragment(R.layout.fragment_passwords) {

    private lateinit var encryptedData: Pair<ByteArray, ByteArray>

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val textToEncode = view.findViewById<EditText>(R.id.testET)
        val encodeBtn = view.findViewById<Button>(R.id.encodeBtn)
        val decodeBtn = view.findViewById<Button>(R.id.decodeBtn)
        val encodedText = view.findViewById<TextView>(R.id.encodedTextTV)
        val decodedText = view.findViewById<TextView>(R.id.decodedTextTV)

        encodeBtn.setOnClickListener {
            if(textToEncode.text.toString().isNotEmpty()){
                if(!Cryptography.isKeyGenerated()) {
                    Cryptography.generateKey()
                    Log.d("TEST", "Utworzono klucz")
                }
                else{
                    Log.d("TEST", "Pobrano istniejący klucz")
                }
                encryptedData = Cryptography.encryptData(textToEncode.text.toString())
                Toast.makeText(context, "Data is encrypted", Toast.LENGTH_SHORT).show()
            }

        }

        decodeBtn.setOnClickListener {
            val result = Cryptography.decryptedData(encryptedData.first, encryptedData.second)
            decodedText.text = result
        }

    }

}