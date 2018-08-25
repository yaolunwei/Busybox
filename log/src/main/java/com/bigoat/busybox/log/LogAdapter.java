package com.bigoat.busybox.log;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Created by bigoat on 2018/8/25.
 *
 * 日志打印适配器
 *
 * 安卓终端日志输出 {@link AndroidLogAdapter}
 *
 * 保存日志到磁盘 {@link DiskLogAdapter}
 *
 * 上传到服务器 {@link NetworkLogAdapter}
 *
 * 输出到指定的View或自定义View显示 {@link ViewLogAdapter}
 *
 * 内网广播日志 {@link BroadcastLogAdapter}
 *
 */

public interface LogAdapter {

    /**
     * Used to determine whether log should be printed out or not.
     *
     * @param priority is the log level e.g. DEBUG, WARNING
     * @param tag is the given tag for the log message
     *
     * @return is used to determine if log should printed.
     *         If it is true, it will be printed, otherwise it'll be ignored.
     */
    boolean isLoggable(int priority, @Nullable String tag);

    /**
     * Each log will use this pipeline
     *
     * @param priority is the log level e.g. DEBUG, WARNING
     * @param tag is the given tag for the log message.
     * @param message is the given message for the log message.
     */
    void log(int priority, @Nullable String tag, @NonNull String message);
}
