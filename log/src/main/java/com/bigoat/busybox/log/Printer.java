package com.bigoat.busybox.log;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Created by bigoat on 2018/8/25.
 *
 * 代理打印日志接口 {@link Log}
 *
 */

public interface Printer {
    void addAdapter(@NonNull LogAdapter adapter);

    Printer t(@Nullable String tag);

    void d(@NonNull String message, @Nullable Object... args);

    void d(@Nullable Object object);

    void e(@NonNull String message, @Nullable Object... args);

    void e(@Nullable Throwable throwable, @NonNull String message, @Nullable Object... args);

    void w(@NonNull String message, @Nullable Object... args);

    void i(@NonNull String message, @Nullable Object... args);

    void v(@NonNull String message, @Nullable Object... args);

    void wtf(@NonNull String message, @Nullable Object... args);

    /**
     * Formats the given json content and print it
     */
    void json(@Nullable String json);

    /**
     * Formats the given xml content and print it
     */
    void xml(@Nullable String xml);

    void log(int priority, @Nullable String tag, @Nullable String message, @Nullable Throwable throwable);

    void clearLogAdapters();

    void removeLogAdapter(LogAdapter adapter);

}
