package com.example.askforleave;

import android.app.ActivityOptions;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.transition.Slide;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;
import com.gyf.barlibrary.ImmersionBar;

public class StartActivity extends AppCompatActivity implements View.OnClickListener {

    private SharedPreferences mPreferences;
    private TextView helpTv;
    private TextView addTv;
    private TextView selectTv;
    private Snackbar mSnackBar;
    private long lastTime = 0; //记录上次点击的时间
    private Dialog mDialog;
    private View mYesBt;
    private boolean mIsFirst;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        getWindow().setEnterTransition(new Slide());
        getWindow().setExitTransition(new Slide());
        setContentView(R.layout.activity_start);
        ImmersionBar.with(this).statusBarDarkFont(true).init();
        mPreferences = getSharedPreferences("userData", MODE_PRIVATE);
        initView();
        mIsFirst = mPreferences.getBoolean("isFirst", true);
        if (mIsFirst) {
            mDialog.show();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        boolean isAdd = mPreferences.getBoolean("isAdd", false);
        if (isAdd) {
            selectTv.setEnabled(true);
            selectTv.setBackgroundResource(R.drawable.shape_bt_enable_bg);
        } else {
            selectTv.setEnabled(false);
            selectTv.setBackgroundResource(R.drawable.shape_bt_unable_bg);
        }
    }

    @Override
    public void onBackPressed() {
        if ((System.currentTimeMillis() - lastTime) > 2000) {
            lastTime = System.currentTimeMillis();
            mSnackBar.show();
            return;
        }
        super.onBackPressed();
    }

    public void onHelp(View view) {
        mDialog.show();
    }

    public void addLeave(View view) {
        Intent intent = new Intent(this, AddDataActivity.class);
        startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
    }

    public void selectLeave(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
    }

    private void initView() {
        helpTv = (TextView) findViewById(R.id.help_tv);
        addTv = (TextView) findViewById(R.id.add_tv);
        selectTv = (TextView) findViewById(R.id.select_tv);
        //back
        mSnackBar = Snackbar.make(getWindow().getDecorView(), "再按一次退出", Snackbar.LENGTH_SHORT);
        TextView message = mSnackBar.getView().findViewById(R.id.snackbar_text);
        message.setTextAlignment(View.TEXT_ALIGNMENT_GRAVITY);
        message.setGravity(Gravity.CENTER);
        //help
        mDialog = new Dialog(this);
        View inflate = View.inflate(this, R.layout.dialog_help, null);
        mYesBt = inflate.findViewById(R.id.yes_bt);
        mYesBt.setOnClickListener(this);
        mDialog.setContentView(inflate);
        mDialog.setCanceledOnTouchOutside(false);
        mDialog.getWindow().getDecorView().setBackgroundColor(Color.TRANSPARENT);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.yes_bt) {
            mDialog.dismiss();
            if (mIsFirst) {
                SharedPreferences.Editor edit = mPreferences.edit();
                edit.putBoolean("isFirst", false);
                edit.apply();
            }
        }
    }
}