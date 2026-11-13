package com.android.launcher2.sdk;

import android.content.Context;
import android.content.Intent;

/** Activity launch bridge backed by Android's public application APIs. */
public final class SPatchActivityManager {
    private SPatchActivityManager() { }

    public static Intent obtainLaunchIntent(Context context, String packageName) {
        return SPatchPackageManager.getLaunchIntentForPackage(context, packageName);
    }
}
