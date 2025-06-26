package com.tahn.data.local.storage

import android.content.Context
import com.tahn.domain.provider.DispatcherProvider
import kotlinx.coroutines.withContext

internal interface AppPreferences {
    suspend fun <T> saveValue(
        shareKey: AppPreferenceKey,
        value: T,
    )

    fun <T> getValue(
        shareKey: AppPreferenceKey,
        default: T,
    ): T
}

internal class AppPreferencesImpl(
    context: Context,
    private val dispatcherProvider: DispatcherProvider,
) : AppPreferences {
    private val sharedPreferences =
        context.getSharedPreferences(context.packageName, Context.MODE_PRIVATE)

    override suspend fun <T> saveValue(
        shareKey: AppPreferenceKey,
        value: T,
    ) {
        withContext(dispatcherProvider.io()) {
            sharedPreferences.edit().apply {
                val key = shareKey.name
                when (value) {
                    is Int -> putInt(key, value)
                    is Long -> putLong(key, value)
                    is Boolean -> putBoolean(key, value)
                    is String -> putString(key, value)
                    is Float, is Double -> putFloat(key, value.toFloat())
                    is Set<*> -> putStringSet(key, value.convert())
                }
                apply()
            }
        }
    }

    override fun <T> getValue(
        shareKey: AppPreferenceKey,
        default: T,
    ): T =
        try {
            sharedPreferences
                .let {
                    val key = shareKey.name
                    when (default) {
                        is Int -> it.getInt(key, default as Int)
                        is Long -> it.getLong(key, default as Long)
                        is Boolean -> it.getBoolean(key, default as Boolean)
                        is String -> it.getString(key, default as String)
                        is Float, is Double -> it.getFloat(key, default as Float)
                        is Set<*> -> it.getStringSet(key, null)
                        else -> default
                    }
                }?.convert<T>() ?: default
        } catch (_: Exception) {
            default
        }

    @Suppress("UNCHECKED_CAST")
    private fun <T> Any?.convert() = this as? T

    enum class AppPrefKey {
        LONG_LAST_TIME_FETCH_USER,
    }
}

internal typealias AppPreferenceKey = AppPreferencesImpl.AppPrefKey
