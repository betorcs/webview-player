package org.devmaster.player;

import android.os.Build;

public final class AndroidUtils {

    public static boolean isBeforeKitkat() {
        return Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT;
    }

    private AndroidUtils() {}
}
