package com.bigoat.busybox.log.format;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.bigoat.busybox.log.FormatStrategy;
import com.bigoat.busybox.log.LogStrategy;
import com.bigoat.busybox.log.Utils;
import com.bigoat.busybox.log.out.ViewLogStrategy;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import static com.bigoat.busybox.log.Utils.checkNotNull;

public class ViewFormatStrategy implements FormatStrategy {

    private static final String NEW_LINE = System.getProperty("line.separator");
    private static final String NEW_LINE_REPLACEMENT = " <br> ";
    private static final String SEPARATOR = ",";

    @NonNull
    private final Date date;
    @NonNull
    private final SimpleDateFormat dateFormat;
    @NonNull
    private final LogStrategy logStrategy;
    @Nullable
    private final String tag;



    private ViewFormatStrategy(@NonNull ViewFormatStrategy.Builder builder) {
        checkNotNull(builder);

        date = builder.date;
        dateFormat = builder.dateFormat;
        logStrategy = builder.logStrategy;
        tag = builder.tag;
    }


    @NonNull
    public static ViewFormatStrategy.Builder newBuilder(Context c) {
        return new ViewFormatStrategy.Builder(c);
    }

    @Override
    public void log(int priority, @Nullable String onceOnlyTag, @NonNull String message) {
        checkNotNull(message);

        String tag = formatTag(onceOnlyTag);

        date.setTime(System.currentTimeMillis());

        StringBuilder builder = new StringBuilder();

        // machine-readable date/time
        builder.append(Long.toString(date.getTime()));

        // human-readable date/time
        builder.append(SEPARATOR);
        builder.append(dateFormat.format(date));

        // level
        builder.append(SEPARATOR);
        builder.append(Utils.logLevel(priority));

        // tag
        builder.append(SEPARATOR);
        builder.append(tag);

        // message
        if (message.contains(NEW_LINE)) {
            // a new line would break the CSV format, so we replace it here
            message = message.replaceAll(NEW_LINE, NEW_LINE_REPLACEMENT);
        }
        builder.append(SEPARATOR);
        builder.append(message);

        // new line
        builder.append(NEW_LINE);

        logStrategy.log(priority, tag, message);

    }

    @Nullable
    private String formatTag(@Nullable String tag) {
        if (!Utils.isEmpty(tag) && !Utils.equals(this.tag, tag)) {
            return this.tag + "-" + tag;
        }
        return this.tag;
    }

    public static final class Builder {
        private static final int MAX_LINE = 1024;
        Date date;
        SimpleDateFormat dateFormat;
        LogStrategy logStrategy;
        String tag = "VIEW";

        Context context;

        private Builder(Context c) {
            context = c;
        }

        @NonNull
        public ViewFormatStrategy.Builder date(@Nullable Date val) {
            date = val;
            return this;
        }

        @NonNull
        public ViewFormatStrategy.Builder dateFormat(@Nullable SimpleDateFormat val) {
            dateFormat = val;
            return this;
        }

        @NonNull
        public ViewFormatStrategy.Builder logStrategy(@Nullable LogStrategy val) {
            logStrategy = val;
            return this;
        }

        @NonNull
        public ViewFormatStrategy.Builder tag(@Nullable String tag) {
            this.tag = tag;
            return this;
        }

        @NonNull
        public ViewFormatStrategy build() {
            if (date == null) {
                date = new Date();
            }

            if (dateFormat == null) {
                dateFormat = new SimpleDateFormat("dd", Locale.UK);
            }

            logStrategy = new ViewLogStrategy(context);

            return new ViewFormatStrategy(this);
        }
    }
}
