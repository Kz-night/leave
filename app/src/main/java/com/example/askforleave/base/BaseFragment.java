package com.example.askforleave.base;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.askforleave.R;
import com.example.askforleave.utils.TimeLoad;

/**
 * Created by AK on 2021/3/13 15:42.
 * God bless my code!
 */
public abstract class BaseFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = onSubViewLoad(inflater, container);
        initTopView(rootView);
        return rootView;

    }

    private void initTopView(View view) {
        WebView mFangWeiWebView = view.findViewById(R.id.fang_wei_WebView);
        final TextView mNowTimeTv = view.findViewById(R.id.now_time_tv);
        final TextView timeBigTv = view.findViewById(R.id.time_big_tv);

        final Handler handler = new Handler(Looper.getMainLooper());
        handler.post(new Runnable() {
            @Override
            public void run() {
                String timeText = TimeLoad.getNowTimeText();
                mNowTimeTv.setText(timeText);
                if (timeBigTv != null) {
                    timeBigTv.setText(timeText);
                }
                handler.postDelayed(this, 1000);
            }
        });

        WebSettings settings = mFangWeiWebView.getSettings();
        settings.setDisplayZoomControls(false);
        mFangWeiWebView.loadUrl("file:///android_asset/index.html");
    }

    protected abstract View onSubViewLoad(LayoutInflater inflater, ViewGroup container);

}
