package com.bigoat.busybox.log.out;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.webkit.URLUtil;

import com.bigoat.busybox.log.LogStrategy;


import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import static com.bigoat.busybox.log.Utils.checkNotNull;

public class NetworkLogStrategy implements LogStrategy {

    @Override
    public void log(int priority, @Nullable String tag, @NonNull String message) {

    }

    /**
     * 上传日志
     */
    private void upload(@NonNull String urlStr, @NonNull String content) throws IOException {
        checkNotNull(urlStr);
        checkNotNull(content);

        if (!URLUtil.isHttpUrl(urlStr) || !URLUtil.isHttpsUrl(urlStr)) {
            throw new NullPointerException();
        }

        URL url = new URL(urlStr);

        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            urlConnection.setDoOutput(true);
            urlConnection.setChunkedStreamingMode(0);

            OutputStream out = new BufferedOutputStream(urlConnection.getOutputStream());
            writeStream(out, content);
        } finally {
            urlConnection.disconnect();
        }




    }

    private void writeStream(OutputStream out, String content) throws IOException {
        checkNotNull(content);
        out.write(content.getBytes("UTF-8"));
        out.flush();
    }

}
