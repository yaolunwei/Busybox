package com.bigoat.busybox.log;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.bigoat.busybox.log.format.CsvFormatStrategy;
import com.bigoat.busybox.log.format.PrettyFormatStrategy;

/**
 * Used to determine how messages should be printed or saved.
 *
 * @see PrettyFormatStrategy
 * @see CsvFormatStrategy
 */
public interface FormatStrategy {

  void log(int priority, @Nullable String tag, @NonNull String message);
}
