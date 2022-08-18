package com.ahmed.shortener.ui

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.widget.RadioButton
import androidx.appcompat.app.AppCompatActivity
import com.ahmed.shortener.R
import com.ahmed.shortener.data.Language
import com.ahmed.shortener.databinding.FragmentSettingsBinding
import com.ahmed.shortener.databinding.LayoutLanguagesBinding
import com.ahmed.shortener.utils.Constants
import com.ahmed.shortener.utils.navigateToFragment
import com.ahmed.shortener.utils.showShortToast
import com.google.android.material.bottomsheet.BottomSheetDialog

class SettingsFragment : BaseFragment<FragmentSettingsBinding>(FragmentSettingsBinding::inflate) {
    override val logTag: String = Constants.MAIN_ACTIVITY_LOG_TAG

    override fun setUpOnCreateView() {
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
        val sharedPreferences = requireContext().getSharedPreferences(Constants.PREF_DB_NAME, Context.MODE_PRIVATE)
        val language = sharedPreferences.getString(Constants.PREF_TITLE_LANG, Constants.LANGUAGE_DEFAULT)
        return if (language == Constants.ENGLISH_LANGUAGE_CODE) Language.ENGLISH else Language.ARABIC
    }

    private fun handleChangeLanguage(newLang: Language) {
        val languageCode: String = if (newLang == Language.ENGLISH) Constants.ENGLISH_LANGUAGE_CODE
        else Constants.ARABIC_LANGUAGE_CODE
        requireContext().getSharedPreferences(Constants.PREF_DB_NAME, Context.MODE_PRIVATE).run {
            with(this.edit()) {
                putString(Constants.PREF_TITLE_LANG, languageCode)
            }
        }
        requireActivity().recreate()
    }
}