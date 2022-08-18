package com.ahmed.shortener.utils

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.os.Build
import android.view.View
import android.webkit.URLUtil
import android.widget.Toast
import androidx.annotation.IdRes
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.ahmed.shortener.R
import com.google.android.material.snackbar.Snackbar

fun isValidUrl(url: String): Boolean = URLUtil.isNetworkUrl(url) && URLUtil.isValidUrl(url)

fun showDefaultLongSnackBar(context: Context, rootLayout: View, message: String) = Snackbar.make(rootLayout, message, Snackbar.LENGTH_LONG).setBackgroundTint(context.getColor(R.color.light_orange)).show()

fun showShortToast(context: Context, message: String) = Toast.makeText(context, message, Toast.LENGTH_SHORT).show()

fun copyShortenedUrlToClipboard(context: Context, shortenedUrl: String) {
    val clipboardManager = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
    clipboardManager.setPrimaryClip(ClipData.newPlainText(Constants.CLIP_LABEL, shortenedUrl))
    if (isAndroid12AndLower()) showShortToast(context, "Url copied!")
}

fun isAndroid12AndLower(): Boolean = Build.VERSION.SDK_INT <= Build.VERSION_CODES.S_V2

fun shareShortenedUrl(context: Context, shortenedUrl: String) = Intent(Intent.ACTION_SEND).run {
        type = Constants.INTENT_TYPE
        putExtra(Intent.EXTRA_TEXT, shortenedUrl)
        context.startActivity(Intent.createChooser(this, Constants.SHARE_TITLE))
    }

fun navigateToFragment(activity: AppCompatActivity, @IdRes fragmentManagerId: Int, fragment: Fragment) = activity.supportFragmentManager.beginTransaction().replace(fragmentManagerId, fragment).commit()