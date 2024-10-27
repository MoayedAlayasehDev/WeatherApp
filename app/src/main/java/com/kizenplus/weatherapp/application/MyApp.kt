package com.kizenplus.weatherapp.application

import android.app.Application
import android.content.Context
import android.content.res.Configuration
import android.text.TextUtils
import com.kizenplus.weatherapp.utils.SharedPreferencesHelper
import dagger.hilt.android.HiltAndroidApp
import java.util.Locale

@HiltAndroidApp
class MyApp : Application() {
    companion object {
        lateinit var instance: MyApp
            private set
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }

    fun updateLanguage(ctx: Context) {
        val lang: String = SharedPreferencesHelper.getString(
            instance,
            SharedPreferencesHelper.LANGUAGE_KEY,
            SharedPreferencesHelper.DEFAULT_LANGUAGE
        )
        updateLanguage(ctx, lang)
    }

    fun updateLanguage(ctx: Context, lang: String) {
        SharedPreferencesHelper.putString(
            ctx, SharedPreferencesHelper.LANGUAGE_KEY, lang
        )
        val cfg = Configuration()
        if (!TextUtils.isEmpty(lang)) {
            val locale = Locale(lang)
            cfg.locale = locale
        } else cfg.locale = Locale.getDefault()
        ctx.resources.updateConfiguration(cfg, null)
    }
}
