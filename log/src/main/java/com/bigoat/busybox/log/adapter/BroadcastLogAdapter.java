package com.bigoat.busybox.log.adapter;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.bigoat.busybox.log.LogAdapter;

/**
 * Created by bigoat on 2018/8/26.
 */

public class BroadcastLogAdapter implements LogAdapter {
    @Override
    public boolean isLoggable(int priority, @Nullable String tag) {
        return false;
    }

    @Override
    public void log(int priority, @Nullable String tag, @NonNull String message) {

    }
}
