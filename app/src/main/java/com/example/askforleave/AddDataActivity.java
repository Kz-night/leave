package com.example.askforleave;

import android.app.ActivityOptions;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.bigkoo.pickerview.view.TimePickerView;
import com.gyf.barlibrary.ImmersionBar;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class AddDataActivity extends AppCompatActivity {

    private SharedPreferences mPreferences;
    private RelativeLayout leaveTypeBt;
    private TextView leaveTypeTv;
    private RelativeLayout startTimeBt;
    private TextView startTimeTv;
    private EditText goOutTimeEdt;
    private EditText leaveReasonEdt;
    private EditText CCManEdt;
    private EditText positionEdt;
    private TextView commitInfoBt;
    private ImageView backBt;
    private OptionsPickerView<Object> mOptionsPickerView;
    private List<Object> mLeaveTypeList;
    private TimePickerView mTimePickerView;
    private EditText identityEdt;
    private EditText contactNumberEdt;
    private String mDef_select;
    private EditText userNameDet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_data);
        ImmersionBar.with(this).statusBarDarkFont(true).init();
        initView();
        mDef_select = getString(R.string.def_select_text);
        mPreferences = getSharedPreferences("userData", MODE_PRIVATE);
        boolean isAdd = mPreferences.getBoolean("isAdd", false);
        if (isAdd) {
            initData();
        }
        initEvent();
    }

    private void initEvent() {
        backBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finishAfterTransition();
            }
        });

        leaveTypeBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOptionsPickerView.show();
            }
        });

        startTimeBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTimePickerView.show();
            }
        });

        commitInfoBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(userNameDet.getText().toString())) {
                    sendToast("你的名字");
                    return;
                }

                if (leaveTypeTv.getText().toString().equals(mDef_select)) {
                    sendToast("请假类型");
                    return;
                }

                if (startTimeTv.getText().toString().equals(mDef_select)) {
                    sendToast("开始时间");
                    return;
                }

                if (TextUtils.isEmpty(goOutTimeEdt.getText().toString())) {
                    sendToast("外出时间");
                    return;
                }

                if (TextUtils.isEmpty(CCManEdt.getText().toString())) {
                    sendToast("审批人");
                    return;
                }

                if (TextUtils.isEmpty(identityEdt.getText().toString())) {
                    sendToast("审批身份");
                    return;
                }

                if (TextUtils.isEmpty(positionEdt.getText().toString())) {
                    sendToast("定位信息");
                    return;
                }

                if (TextUtils.isEmpty(contactNumberEdt.getText().toString())) {
                    sendToast("紧急联系人");
                    return;
                }

                if (TextUtils.isEmpty(leaveReasonEdt.getText().toString())) {
                    sendToast("请假原因");
                    return;
                }

                //保存数据
                SharedPreferences.Editor edit = mPreferences.edit();
                edit.putString("user_name", userNameDet.getText().toString());
                edit.putInt("leave_type", mLeaveTypeList.indexOf(leaveTypeTv.getText().toString()));
                edit.putString("start_time", startTimeTv.getText().toString());
                edit.putInt("go_out_time", Integer.parseInt(goOutTimeEdt.getText().toString()));
                edit.putString("CC_man", CCManEdt.getText().toString());
                edit.putString("identity", identityEdt.getText().toString());
                edit.putString("position", positionEdt.getText().toString());
                edit.putString("contact_number", contactNumberEdt.getText().toString());
                edit.putString("leave_reason", leaveReasonEdt.getText().toString());
                edit.putBoolean("isAdd", true);
                boolean commit = edit.commit();
                if (commit) {
                    Toast.makeText(AddDataActivity.this, "添加成功", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(AddDataActivity.this, MainActivity.class);
                    startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(AddDataActivity.this).toBundle());
                    finishAfterTransition();
                } else {
                    Toast.makeText(AddDataActivity.this, "添加失败，未知错误", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void initData() {
        String user_name = mPreferences.getString("user_name", null);
        int leave_type = mPreferences.getInt("leave_type", -1);
        String start_time = mPreferences.getString("start_time", null);
        int go_out_time = mPreferences.getInt("go_out_time", -1);
        String cc_man = mPreferences.getString("CC_man", null);
        String identity = mPreferences.getString("identity", null);
        String position = mPreferences.getString("position", null);
        String contact_number = mPreferences.getString("contact_number", null);
        String leave_reason = mPreferences.getString("leave_reason", null);

        if (user_name != null) {
            userNameDet.setText(user_name);
        }

        if (leave_type != -1) {
            leaveTypeTv.setText(mLeaveTypeList.get(leave_type).toString());
        }

        if (start_time != null) {
            startTimeTv.setText(start_time);
        }

        if (go_out_time != -1) {
            goOutTimeEdt.setText(go_out_time + "");
        }

        if (cc_man != null) {
            CCManEdt.setText(cc_man);
        }

        if (identity != null) {
            identityEdt.setText(identity);
        }

        if (position != null) {
            positionEdt.setText(position);
        }

        if (contact_number != null) {
            contactNumberEdt.setText(contact_number);
        }

        if (leave_reason != null) {
            leaveReasonEdt.setText(leave_reason);
        }

    }

    private void initView() {
        leaveTypeBt = (RelativeLayout) findViewById(R.id.leave_type_bt);
        leaveTypeTv = (TextView) findViewById(R.id.leave_type_tv);
        startTimeBt = (RelativeLayout) findViewById(R.id.start_time_bt);
        startTimeTv = (TextView) findViewById(R.id.start_time_tv);
        goOutTimeEdt = (EditText) findViewById(R.id.go_out_time_edt);
        leaveReasonEdt = (EditText) findViewById(R.id.leave_reason_edt);
        CCManEdt = (EditText) findViewById(R.id.CC_man_edt);
        positionEdt = (EditText) findViewById(R.id.position_edt);
        commitInfoBt = (TextView) findViewById(R.id.commit_info_bt);
        backBt = (ImageView) findViewById(R.id.back_bt);
        identityEdt = (EditText) findViewById(R.id.identity_edt);
        contactNumberEdt = (EditText) findViewById(R.id.contact_number_edt);
        userNameDet = (EditText) findViewById(R.id.user_name_det);
        initPiKerView();
    }

    private void initPiKerView() {
        OptionsPickerBuilder optionsPickerBuilder = new OptionsPickerBuilder(this, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                leaveTypeTv.setText(mLeaveTypeList.get(options1).toString());
            }
        });
        optionsPickerBuilder.setSubmitText("确定");
        optionsPickerBuilder.setCancelText("取消");
        optionsPickerBuilder.setTitleText("请假类型");
        optionsPickerBuilder.setTitleSize(16);
        optionsPickerBuilder.setSubCalSize(13);
        optionsPickerBuilder.setContentTextSize(13);
        optionsPickerBuilder.setTitleBgColor(0xFF666666);//标题背景颜色 Night mode
        optionsPickerBuilder.setBgColor(0xFF333333);//滚轮背景颜色 Night mode
        optionsPickerBuilder.setTitleColor(Color.WHITE);
        optionsPickerBuilder.setSubmitColor(Color.WHITE);
        optionsPickerBuilder.setCancelColor(Color.WHITE);
        optionsPickerBuilder.setTextColorCenter(Color.WHITE);
        mOptionsPickerView = optionsPickerBuilder.build();
        mLeaveTypeList = Arrays.<Object>asList(getResources().getStringArray(R.array.leave_type));
        mOptionsPickerView.setPicker(mLeaveTypeList);

        TimePickerBuilder timePickerBuilder = new TimePickerBuilder(this, new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault());
                startTimeTv.setText(dateFormat.format(date));
            }
        });

        timePickerBuilder.setType(new boolean[]{true, true, true, true, true, false});
        timePickerBuilder.setTitleText("开始时间"); //标题文字
        timePickerBuilder.setTitleSize(16);
        timePickerBuilder.setSubCalSize(13);
        timePickerBuilder.setContentTextSize(13);
        timePickerBuilder.setTitleBgColor(0xFF666666);//标题背景颜色 Night mode
        timePickerBuilder.setBgColor(0xFF333333);//滚轮背景颜色 Night mode
        timePickerBuilder.setTitleColor(Color.WHITE);
        timePickerBuilder.setSubmitColor(Color.WHITE);//确定按钮文字颜色
        timePickerBuilder.setCancelColor(Color.WHITE);//取消按钮文字颜色
        timePickerBuilder.setTextColorCenter(Color.WHITE);
        mTimePickerView = timePickerBuilder.build();
    }

    private void sendToast(String content) {
        Toast.makeText(this, "请填写" + content, Toast.LENGTH_SHORT).show();
    }

}