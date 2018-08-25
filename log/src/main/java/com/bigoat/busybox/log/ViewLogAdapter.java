package com.bigoat.busybox.log;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Created by bigoat on 2018/8/26.
 */

public class ViewLogAdapter implements LogAdapter {
    @Override
    public boolean isLoggable(int priority, @Nullable String tag) {
        return false;
    }

    @Override
    public void log(int priority, @Nullable String tag, @NonNull String message) {

    }
}
