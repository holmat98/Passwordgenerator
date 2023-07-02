package com.mateuszholik.passwordgenerator.ui.licenses

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import com.google.android.gms.oss.licenses.OssLicensesMenuActivity
import com.mateuszholik.passwordgenerator.R
import com.mateuszholik.passwordgenerator.databinding.FragmentLicensesDetailsBinding
import com.mateuszholik.passwordgenerator.extensions.viewBinding
import com.mateuszholik.passwordgenerator.ui.base.BaseFragment

class LicensesFragment : BaseFragment(R.layout.fragment_licenses_details) {

    private val binding by viewBinding(FragmentLicensesDetailsBinding::bind)

    override val isBottomNavVisible: Boolean = true

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.allLicencesButton.apply {
            onClick = { openActivityWithLicenses() }
            setOnClickListener { openActivityWithLicenses() }
        }

        binding.animationsLicensesButton.apply {
            onClick = { openWebsite(LOTTIE_FILES_LICENSE_LINK) }
            setOnClickListener { openWebsite(LOTTIE_FILES_LICENSE_LINK) }
        }

        binding.fontLicensesButton.apply {
            onClick = { openWebsite(FONT_LICENSE_LINK) }
            setOnClickListener { openWebsite(FONT_LICENSE_LINK) }
        }

        binding.graphicsLicensesButton.apply {
            onClick = { openWebsite(GRAPHICS_LICENSE_LINK) }
            setOnClickListener { openWebsite(GRAPHICS_LICENSE_LINK) }
        }
    }

    private fun openActivityWithLicenses() {
        context?.let {
            startActivity(Intent(it, OssLicensesMenuActivity::class.java))
            OssLicensesMenuActivity.setActivityTitle(getString(R.string.licenses_libraries))
        }
    }

    private fun openWebsite(url: String) {
        val intent = Intent(Intent.ACTION_VIEW).apply {
            data = Uri.parse(url)
        }
        startActivity(intent)
    }

    private companion object {
        const val LOTTIE_FILES_LICENSE_LINK = "https://lottiefiles.com/page/license"
        const val FONT_LICENSE_LINK = "https://scripts.sil.org/cms/scripts/page.php?site_id=nrsi&id=OFL"
        const val GRAPHICS_LICENSE_LINK = "https://undraw.co/license"
    }
}
