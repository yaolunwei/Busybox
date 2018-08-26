package com.bigoat.busybox.log.adapter;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.bigoat.busybox.log.format.CsvFormatStrategy;
import com.bigoat.busybox.log.FormatStrategy;
import com.bigoat.busybox.log.LogAdapter;

import static android.support.v4.util.Preconditions.checkNotNull;

/**
 * This is used to saves log messages to the disk.
 * By default it uses {@link CsvFormatStrategy} to translates text message into CSV format.
 */
public class DiskLogAdapter implements LogAdapter {

  @NonNull
  private final FormatStrategy formatStrategy;

  public DiskLogAdapter() {
    formatStrategy = CsvFormatStrategy.newBuilder().build();
  }

  public DiskLogAdapter(@NonNull FormatStrategy formatStrategy) {
    this.formatStrategy = checkNotNull(formatStrategy);
  }

  @Override public boolean isLoggable(int priority, @Nullable String tag) {
    return true;
  }

  @Override public void log(int priority, @Nullable String tag, @NonNull String message) {
    formatStrategy.log(priority, tag, message);
  }
}