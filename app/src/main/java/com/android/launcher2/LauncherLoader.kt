package com.android.launcher2

import android.content.Context
import android.content.Intent
import com.android.launcher2.sdk.SPatchCore
import com.android.launcher2.sdk.SPatchPackageManager

/**
 * Compatibility facade for launcher code that previously waited for the
 * proprietary virtual-app engine.
 *
 * In standalone mode there is no virtual engine; Android's PackageManager
 * supplies launch intents for applications installed on the device.
 */
object LauncherLoader {
    @JvmStatic
    fun instance() = this

    fun waitForAndGetInitialService(serviceCallback: () -> Unit) {
        SPatchCore.runWhenReady { serviceCallback() }
    }

    fun obtainLaunchIntentByPackage(context: Context, packageName: String): Intent? {
        return SPatchPackageManager.getLaunchIntentForPackage(context, packageName)
    }
}
