package com.android.launcher2.sdk;

/**
 * Minimal lifecycle bridge for the standalone SPatch launcher.
 *
 * The proprietary virtual-app engine is not included in this repository.
 * This class deliberately exposes only lifecycle behavior that can be
 * implemented safely by a normal Android application.
 */
public final class SPatchCore {
    private SPatchCore() { }

    /** Runs the callback immediately because the standalone runtime has no service to await. */
    public static void runWhenReady(Runnable callback) {
        if (callback != null) {
            callback.run();
        }
    }

    /** Indicates that no virtual-app engine is bundled with this build. */
    public static boolean isVirtualizationAvailable() {
        return false;
    }
}
