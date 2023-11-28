package com.guresberatcan.data.util

import android.os.Build
import android.os.Bundle
import android.os.Parcelable

/**
 * Extension function to retrieve a Parcelable value from a Bundle based on the provided key.
 *
 * @param key The key used to store the Parcelable value in the Bundle.
 * @return The Parcelable value retrieved from the Bundle, or null if not found.
 */
inline fun <reified T : Parcelable> Bundle.parcelable(key: String): T? = when {
    // If the SDK version is 31 (Android 12) or higher, use the modern getParcelable method
    Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU -> getParcelable(key, T::class.java)
    // For older SDK versions, use the deprecated getParcelable method
    else -> @Suppress("DEPRECATION") getParcelable(key) as? T
}
