package com.mateuszholik.passwordgenerator.ui.transfer

import com.mateuszholik.passwordgenerator.R
import com.mateuszholik.passwordgenerator.databinding.FragmentTransferPasswordHostBinding
import com.mateuszholik.passwordgenerator.extensions.viewBinding
import com.mateuszholik.passwordgenerator.ui.base.BaseFragment

class TransferPasswordHostFragment : BaseFragment(R.layout.fragment_transfer_password_host) {

    private val binding by viewBinding(FragmentTransferPasswordHostBinding::bind)

    override val isBottomNavVisible: Boolean = true

}