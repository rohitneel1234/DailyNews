package com.rohitneel.dailynews

import android.content.res.Resources
import android.os.Build
import android.util.Log
import kotlin.math.roundToInt

actual class Platform {
    actual val osName: String
        get() = "Android"

    actual val osVersion: String
        get() = Build.VERSION.RELEASE ?: "Unknown"

    actual val deviceModel: String
        get() = "${Build.MANUFACTURER} ${android.os.Build.MODEL}"

    actual val density: Int
        get() = Resources.getSystem().displayMetrics.density.roundToInt()

    actual fun logSystemInfo() {
        Log.d(
            "PlatformInfo",
            "osName: $osName, osVersion: $osVersion, deviceModel: $deviceModel, density: $density"
        )
    }
}