package com.bigoat.busybox.log.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.bigoat.busybox.log.FormatStrategy;
import com.bigoat.busybox.log.LogAdapter;
import com.bigoat.busybox.log.format.ViewFormatStrategy;

import static com.bigoat.busybox.log.Utils.checkNotNull;


/**
 * Created by bigoat on 2018/8/26.
 */

public class ViewLogAdapter implements LogAdapter {
    @NonNull
    private final FormatStrategy formatStrategy;

    public ViewLogAdapter(Context c) {
        formatStrategy = ViewFormatStrategy.newBuilder(c).build();
    }

    public ViewLogAdapter(@NonNull FormatStrategy formatStrategy) {
        this.formatStrategy = checkNotNull(formatStrategy);
    }

    @Override
    public boolean isLoggable(int priority, @Nullable String tag) {
        return true;
    }

    @Override
    public void log(int priority, @Nullable String tag, @NonNull String message) {
        formatStrategy.log(priority, tag, message);
    }
}
