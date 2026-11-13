package com.android.launcher2.sdk;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;

/** Android PackageManager helpers used by the standalone SPatch launcher. */
public final class SPatchPackageManager {
    private SPatchPackageManager() { }

    /** Returns the normal Android launch intent for an installed package. */
    public static Intent getLaunchIntentForPackage(Context context, String packageName) {
        if (context == null || packageName == null || packageName.trim().isEmpty()) {
            return null;
        }
        PackageManager packageManager = context.getPackageManager();
        return packageManager.getLaunchIntentForPackage(packageName);
    }

    /** Standalone mode does not maintain a separate set of virtual applications. */
    public static boolean isInnerAppInstalled(String packageName) {
        return false;
    }
}
