package com.example.askforleave;

import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.askforleave.views.FragmentCreator;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;
import com.gyf.barlibrary.ImmersionBar;

import static com.example.askforleave.views.FragmentCreator.INDEX_DETAIL;

public class MainActivity extends AppCompatActivity {

    private TabLayout myTabLayout;
    private FragmentManager mSupportFragmentManager;
    private FragmentTransaction mFragmentTransaction;
    private TextView feedbackTv;
    private FrameLayout detailContentView;
    private Snackbar mSnackbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ImmersionBar.with(this).statusBarDarkFont(true).init();
        mSupportFragmentManager = getSupportFragmentManager();
        initToolBar();
        initView();
        initEvent();
    }

    private void initToolBar() {
        Toolbar myToolbar = findViewById(R.id.my_toolbar);
        //设置APPBar
        myToolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_ios_24);
        myToolbar.setTitle("");
        setSupportActionBar(myToolbar);
        myToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finishAfterTransition();
            }
        });
    }

    @Override
    public void onBackPressed() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ImmersionBar.with(this).destroy();
    }

    private void initView() {
        myTabLayout = findViewById(R.id.my_tab_layout);
        feedbackTv = findViewById(R.id.feedback_tv);
        detailContentView = (FrameLayout) findViewById(R.id.detail_content_view);
        myTabLayout.addTab(myTabLayout.newTab().setText(R.string.detail_title_text));
        myTabLayout.addTab(myTabLayout.newTab().setText(R.string.qr_code_title_text));
        myTabLayout.setSelectedTabIndicatorColor(getResources().getColor(R.color.detail_check_on_color));
        myTabLayout.setTabTextColors(getResources().getColor(R.color.detail_check_color), getResources().getColor(R.color.detail_check_on_color));
        myTabLayout.setTabGravity(TabLayout.GRAVITY_CENTER);
        changeFragment(INDEX_DETAIL);
        //反馈提示
        mSnackbar = Snackbar.make(detailContentView, "请点击左上角返回按钮退出界面", Snackbar.LENGTH_SHORT);
        TextView message = mSnackbar.getView().findViewById(R.id.snackbar_text);
        message.setTextAlignment(View.TEXT_ALIGNMENT_GRAVITY);
        message.setGravity(Gravity.CENTER);
    }

    private void initEvent() {
        myTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();
                changeFragment(position);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        feedbackTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSnackbar.show();
            }
        });
    }

    private void changeFragment(int position) {
        mFragmentTransaction = mSupportFragmentManager.beginTransaction();
        mFragmentTransaction.replace(R.id.detail_content_view, FragmentCreator.getFragment(position));
        mFragmentTransaction.commit();
    }

}