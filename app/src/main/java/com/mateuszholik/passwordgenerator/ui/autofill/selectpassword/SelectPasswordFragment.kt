package com.mateuszholik.passwordgenerator.ui.autofill.selectpassword

import android.app.Activity
import android.app.assist.AssistStructure
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.autofill.AutofillManager.EXTRA_ASSIST_STRUCTURE
import android.view.autofill.AutofillManager.EXTRA_AUTHENTICATION_RESULT
import androidx.fragment.app.Fragment
import com.mateuszholik.data.repositories.models.AutofillPasswordDetails
import com.mateuszholik.passwordgenerator.R
import com.mateuszholik.passwordgenerator.autofill.builders.FillResponseBuilder
import com.mateuszholik.passwordgenerator.autofill.extensions.getParsedStructure
import com.mateuszholik.passwordgenerator.databinding.FragmentSelectPasswordBinding
import com.mateuszholik.passwordgenerator.extensions.fromParcelable
import com.mateuszholik.passwordgenerator.extensions.viewBinding
import com.mateuszholik.passwordgenerator.ui.autofill.selectpassword.adapter.SelectPasswordAdapter
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class SelectPasswordFragment : Fragment(R.layout.fragment_select_password) {

    private val binding by viewBinding(FragmentSelectPasswordBinding::bind)
    private val viewModel: SelectPasswordViewModel by viewModel()
    private val fillResponseBuilder: FillResponseBuilder by inject()
    private var adapter: SelectPasswordAdapter? = null
    private var selectedPassword: AutofillPasswordDetails? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.confirmSelectedPasswordButton.setOnClickListener {
            activity?.let {
                val intent = it.intent

                val structure: AssistStructure? = intent.fromParcelable(EXTRA_ASSIST_STRUCTURE)
                val parsedStructure = structure?.getParsedStructure()

                if (parsedStructure != null) {
                    selectedPassword?.let { password ->

                        val fillResponse = fillResponseBuilder.addDataset(
                            autofillId = parsedStructure.autofillId,
                            promptMessage = password.platformName,
                            autofillValue = password.password,
                            packageName = it.packageName
                        ).build()

                        val replyIntent = Intent().apply {
                            putExtra(EXTRA_AUTHENTICATION_RESULT, fillResponse)
                        }

                        it.setResult(Activity.RESULT_OK, replyIntent)
                        it.finish()
                    }
                }
            }
        }

        viewModel.passwords.observe(viewLifecycleOwner) {
            adapter?.addPasswords(it)
        }
    }

    override fun onResume() {
        super.onResume()
        adapter = SelectPasswordAdapter {
            selectedPassword = it
            binding.confirmSelectedPasswordButton.apply {
                isEnabled = true
            }
        }
        binding.selectablePasswordsRecyclerView.adapter = adapter
    }

    override fun onPause() {
        super.onPause()
        binding.selectablePasswordsRecyclerView.adapter = null
        adapter = null
    }

}
