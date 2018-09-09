package com.bigoat.busybox.log.out;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bigoat.busybox.log.LogStrategy;
import com.bigoat.busybox.log.R;
import com.bigoat.busybox.log.Utils;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * 在手机屏幕上显示日志
 *
 * 1. 长按移动
 *
 * 2. 双击收缩为原点
 *
 * 3. 点击原点显示日志
 *
 */

public class ViewLogStrategy extends LinearLayout implements LogStrategy {

    private List<MyAdapter.ViewLog> mViewLogList = new ArrayList<>();
    private MyAdapter myAdapter;
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;

    private WindowManager mWindowManager;
    private WindowManager.LayoutParams param;

    private Context context;

    public ViewLogStrategy(Context context) {
        super(context);

        this.context = context;

        recyclerView = new RecyclerView(context);
        linearLayoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(linearLayoutManager);
        myAdapter = new MyAdapter(mViewLogList);
        recyclerView.setAdapter(myAdapter);

        addView(recyclerView);

        showView();
    }

    private void showView() {

        param = new WindowManager.LayoutParams();
        param.type = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;
//        param.type=WindowManager.LayoutParams.TYPE_SYSTEM_ALERT;     // 系统提示类型,重要
        param.format=1;
        param.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE; // 不能抢占聚焦点
        param.flags = param.flags | WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH;
        param.flags = param.flags | WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS; // 排版不受限制
        param.alpha = 1.0f;
        param.gravity= Gravity.LEFT|Gravity.TOP;   //调整悬浮窗口至左上角
        //以屏幕左上角为原点，设置x、y初始值
        param.x=100;
        param.y=100;
        //设置悬浮窗口长宽数据
        param.width=500;
        param.height=800;

        //获取WindowManager
        mWindowManager=(WindowManager)context.getSystemService(Context.WINDOW_SERVICE);
        //设置LayoutParams(全局变量）相关参数

        //显示myFloatView图像
        mWindowManager.addView(this, param);
    }

    @Override
    public void log(int priority, @Nullable String tag, @NonNull String message) {
        if (context == null) {
            mWindowManager.removeView(this);
        }
        mViewLogList.add(new MyAdapter.ViewLog("", Utils.logLevel(priority) + "/", tag + ": ", message));
        linearLayoutManager.scrollToPosition(mViewLogList.size());
        myAdapter.notifyDataSetChanged();
        recyclerView.smoothScrollToPosition(mViewLogList.size());

    }


    static class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder>  {

        private List<ViewLog> mViewLogList;

        static class ViewLog {
            public ViewLog(String dateTime, String level, String tag, String message) {
                this.dateTime = dateTime;
                this.level = level;
                this.tag = tag;
                this.message = message;
            }

            private String dateTime;
            private String level;
            private String tag;
            private String message;
        }

        static class ViewHolder extends RecyclerView.ViewHolder {
            TextView dateTime;
            TextView level;
            TextView tag;
            TextView message;

            ViewHolder(View view) {
                super(view);
                dateTime = view.findViewById(R.id.dateTime);
                level = view.findViewById(R.id.level);
                tag = view.findViewById(R.id.tag);
                message = view.findViewById(R.id.message);
            }
        }

        public MyAdapter(List<ViewLog> viewLogList) {
            mViewLogList = viewLogList;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_view_log, parent, false);
            ViewHolder holder = new ViewHolder(view);
            return holder;
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            ViewLog viewLog = mViewLogList.get(position);
            holder.dateTime.setText(viewLog.dateTime);
            holder.level.setText(viewLog.level);
            holder.tag.setText(viewLog.tag);
            holder.message.setText(viewLog.message);
        }


        @Override
        public int getItemCount() {
            return mViewLogList.size();
        }



    }
}
