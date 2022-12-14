package com.ahmed.shortener.data.services

import android.annotation.TargetApi
import android.content.Context
import android.content.res.Configuration
import android.content.res.Resources
import android.os.Build
import android.util.Log
import androidx.appcompat.app.AppCompatDelegate
import com.ahmed.shortener.utils.Constants
import com.ahmed.shortener.utils.Preferencer
import java.util.*

object LocaleService {
    fun updateBaseContextLocale(context: Context): Context {
        val language = getLanguageFromPreferences(context)
        Log.d("MALT", "Language is $language")
        val locale = Locale(language)
        Locale.setDefault(locale)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) return updateResourcesLocale(context, locale)
        return updateResourcesLocaleLegacy(context, locale)
    }

    @TargetApi(Build.VERSION_CODES.N)
    private fun updateResourcesLocale(
        context: Context,
        locale: Locale
    ): Context {
        val configuration: Configuration = context.resources.configuration
        configuration.setLocale(locale)
        updateAppTheme(context)
        return context.createConfigurationContext(configuration)
    }

    @Suppress(Constants.DEPRECATION)
    private fun updateResourcesLocaleLegacy(
        context: Context,
        locale: Locale
    ): Context {
        val resources: Resources = context.resources
        val configuration: Configuration = resources.configuration
        configuration.locale = locale
        resources.updateConfiguration(configuration, resources.displayMetrics)
        updateAppTheme(context)
        return context
    }

    private fun updateAppTheme(context: Context) {
        val sharedPreferences = context.getSharedPreferences(Constants.PREF_DB_NAME, Context.MODE_PRIVATE)
        when (sharedPreferences.getString(Constants.PREF_TITLE_THEME, Constants.THEME_DEFAULT)) {
            Constants.THEME_DEFAULT -> {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
            else -> {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            }
        }
    }

    private fun getLanguageFromPreferences(context: Context): String {
        return Preferencer(context).getString(Constants.PREF_TITLE_LANG, Constants.LANGUAGE_DEFAULT)
    }
}