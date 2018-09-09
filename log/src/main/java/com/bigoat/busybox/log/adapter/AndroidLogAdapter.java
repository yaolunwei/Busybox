package com.bigoat.busybox.log.adapter;

import android.annotation.SuppressLint;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.bigoat.busybox.log.FormatStrategy;
import com.bigoat.busybox.log.LogAdapter;
import com.bigoat.busybox.log.format.PrettyFormatStrategy;

import static android.support.v4.util.Preconditions.checkNotNull;


/**
 * Created by bigoat on 2018/8/26.
 */

public class AndroidLogAdapter implements LogAdapter {
    @NonNull private final FormatStrategy formatStrategy;

    public AndroidLogAdapter() {
        this.formatStrategy = PrettyFormatStrategy.newBuilder().build();
    }

    @SuppressLint("RestrictedApi")
    public AndroidLogAdapter(@NonNull FormatStrategy formatStrategy) {
        this.formatStrategy = checkNotNull(formatStrategy);
    }

    @Override public boolean isLoggable(int priority, @Nullable String tag) {
        return true;
    }

    @Override public void log(int priority, @Nullable String tag, @NonNull String message) {
        formatStrategy.log(priority, tag, message);
    }

}
