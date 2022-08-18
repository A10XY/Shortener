package com.ahmed.shortener.ui

import android.widget.RadioButton
import androidx.appcompat.app.AppCompatActivity
import com.ahmed.shortener.R
import com.ahmed.shortener.data.Language
import com.ahmed.shortener.databinding.FragmentSettingsBinding
import com.ahmed.shortener.utils.Constants
import com.ahmed.shortener.utils.Preferencer
import com.ahmed.shortener.utils.navigateToFragment
import com.google.android.material.bottomsheet.BottomSheetDialog

class SettingsFragment : BaseFragment<FragmentSettingsBinding>(FragmentSettingsBinding::inflate) {
    override val logTag: String = Constants.MAIN_ACTIVITY_LOG_TAG

    private lateinit var preferencer: Preferencer

    override fun setUpOnCreateView() {
        preferencer = Preferencer(requireContext())
        setCurrentLanguage()
    }

    override fun addCallbacks() {
        binding.changeLanguageLayout.setOnClickListener {
            showChangeLanguageBottomSheet()
        }
        binding.navigateBackIcon.setOnClickListener {
            navigateToFragment(requireActivity() as AppCompatActivity, R.id.fragment_container_view_all, HomeFragment())
        }
    }

    private fun setCurrentLanguage() {
        if (getCurrentLanguage() == Language.ENGLISH) binding.selectedLanguage.text = getString(R.string.lang_en)
        else binding.selectedLanguage.text = getString(R.string.lang_ar)
    }

    private fun showChangeLanguageBottomSheet() {
        BottomSheetDialog(requireContext()).run {
            setContentView(R.layout.layout_languages)
            val englishButton = this.findViewById<RadioButton>(R.id.lang_en)
            englishButton?.setOnClickListener {
                if (getCurrentLanguage() != Language.ENGLISH) handleChangeLanguage(Language.ENGLISH)
            }
            val arabicButton = this.findViewById<RadioButton>(R.id.lang_ar)
            arabicButton?.setOnClickListener {
                if (getCurrentLanguage() != Language.ARABIC) handleChangeLanguage(Language.ARABIC)
            }
            if (getCurrentLanguage() == Language.ENGLISH) englishButton?.isChecked = true
            else arabicButton?.isChecked = true
            show()
        }
    }

    private fun getCurrentLanguage(): Language {
        val language = preferencer.getString(Constants.PREF_TITLE_LANG, Constants.LANGUAGE_DEFAULT)
        return if (language == Constants.ENGLISH_LANGUAGE_CODE) Language.ENGLISH else Language.ARABIC
    }

    private fun handleChangeLanguage(newLang: Language) {
        val languageCode: String = if (newLang == Language.ENGLISH) Constants.ENGLISH_LANGUAGE_CODE else Constants.ARABIC_LANGUAGE_CODE
        preferencer.putString(Constants.PREF_TITLE_LANG, languageCode)
        requireActivity().recreate()
    }
}