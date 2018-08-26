package com.bigoat.busybox.sample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.bigoat.busybox.log.adapter.AndroidLogAdapter;
import com.bigoat.busybox.log.adapter.DiskLogAdapter;
import com.bigoat.busybox.log.FormatStrategy;
import com.bigoat.busybox.log.Log;
import com.bigoat.busybox.log.format.PrettyFormatStrategy;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.d("Tag", "I'm a log which you don't see easily, hehe");
        Log.d("json content", "{ \"key\": 3, \n \"value\": something}");
        Log.d("error", "There is a crash somewhere or any warning");

        Log.addLogAdapter(new AndroidLogAdapter());
        Log.d("message");

        Log.clearLogAdapters();


        FormatStrategy formatStrategy = PrettyFormatStrategy.newBuilder()
                .showThreadInfo(false)  // (Optional) Whether to show thread info or not. Default true
                .methodCount(0)         // (Optional) How many method line to show. Default 2
                .methodOffset(3)        // (Optional) Skips some method invokes in stack trace. Default 5
//        .logStrategy(customLog) // (Optional) Changes the log strategy to print out. Default LogCat
                .tag("My custom tag")   // (Optional) Custom tag for each log. Default PRETTY_LOGGER
                .build();

        Log.addLogAdapter(new AndroidLogAdapter(formatStrategy));

        Log.addLogAdapter(new AndroidLogAdapter() {
            @Override public boolean isLoggable(int priority, String tag) {
                return BuildConfig.DEBUG;
            }
        });

        Log.addLogAdapter(new DiskLogAdapter());


        Log.w("no thread info and only 1 method");

        Log.clearLogAdapters();
        formatStrategy = PrettyFormatStrategy.newBuilder()
                .showThreadInfo(false)
                .methodCount(0)
                .build();

        Log.addLogAdapter(new AndroidLogAdapter(formatStrategy));
        Log.i("no thread info and method info");

        Log.t("tag").e("Custom tag for only one use");

        Log.json("{ \"key\": 3, \"value\": something}");

        Log.d(Arrays.asList("foo", "bar"));

        Map<String, String> map = new HashMap<>();
        map.put("key", "value");
        map.put("key1", "value2");

        Log.d(map);

        Log.clearLogAdapters();
        formatStrategy = PrettyFormatStrategy.newBuilder()
                .showThreadInfo(false)
                .methodCount(0)
                .tag("MyTag")
                .build();
        Log.addLogAdapter(new AndroidLogAdapter(formatStrategy));

        Log.w("my log message with my tag");
    }
}
