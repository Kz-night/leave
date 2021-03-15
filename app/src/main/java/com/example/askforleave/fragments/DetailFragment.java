package com.example.askforleave.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Html;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.askforleave.R;
import com.example.askforleave.base.BaseFragment;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by AK on 2021/3/2 21:56.
 * God bless my code!
 */
public class DetailFragment extends BaseFragment {

    private TextView topTipTextTv;
    private TextView destroyRule;
    private TextView leaveStartTime;
    private TextView leaveEndTime;
    private TextView leaveStep;
    private TextView contactPerson;
    private TextView leaveReason;
    private TextView leavePosition;
    private TextView CCMan;
    private TextView userNameTv;
    private TextView timeOfApplyTv;
    private TextView teacherPassTv;
    private TextView timeOfPassTv;
    private TextView needLeave;
    private TextView realTime;
    private TextView totalTime;
    private SharedPreferences mPreferences;
    private String[] mLeave_type_list;
    private TextView mLeaveType;

    @Override
    protected View onSubViewLoad(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.detailfragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mPreferences = getContext().getSharedPreferences("userData", Context.MODE_PRIVATE);
        initView(view);
        initData();
    }

    private void initData() {
        String user_name = mPreferences.getString("user_name", null);
        mLeave_type_list = getContext().getResources().getStringArray(R.array.leave_type);
        int leave_type = mPreferences.getInt("leave_type", -1);
        String start_time = mPreferences.getString("start_time", null);
        int go_out_time = mPreferences.getInt("go_out_time", -1);
        String cc_man = mPreferences.getString("CC_man", null);
        String identity = mPreferences.getString("identity", null);
        String position = mPreferences.getString("position", null);
        String contact_number = mPreferences.getString("contact_number", null);
        String leave_reason = mPreferences.getString("leave_reason", null);


        topTipTextTv.setText(R.string.how_to_destroy_text);
        //请假类型
        String leave_type_key = getString(R.string.leave_type_key_text);
        SpannableString leave_type_spannableString = new SpannableString(leave_type_key + mLeave_type_list[leave_type]);
        leave_type_spannableString.setSpan(new ForegroundColorSpan(getColor(R.color.value_main_text_color)), leave_type_key.length(), leave_type_spannableString.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        mLeaveType.setText(leave_type_spannableString);
        //需要离校
        String need_leave_key = getString(R.string.need_leave_key_text);
        SpannableString needLeaveSpannableString = new SpannableString(need_leave_key + getString(R.string.need_leave_value_text));
        needLeaveSpannableString.setSpan(new ForegroundColorSpan(getColor(R.color.value_main_text_color)), need_leave_key.length(), needLeaveSpannableString.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        needLeave.setText(needLeaveSpannableString);
        //销假规则
        String destroyRuleKey = getString(R.string.destroy_rule_key_text);
        SpannableString destroyRuleSpannableString = new SpannableString(destroyRuleKey + getString(R.string.rule_value_text));
        destroyRuleSpannableString.setSpan(new ForegroundColorSpan(getColor(R.color.value_rule_text_color)), destroyRuleKey.length(), destroyRuleSpannableString.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        destroyRule.setText(destroyRuleSpannableString);
        //实际休假时间
        String realTimeKey = getString(R.string.real_leave_key_text);
        SpannableString realTimeSpannableString = new SpannableString(realTimeKey + getString(R.string.real_leave_value_text));
        realTimeSpannableString.setSpan(new ForegroundColorSpan(getColor(R.color.value_main_text_color)), realTimeKey.length(), realTimeSpannableString.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        realTime.setText(realTimeSpannableString);
        //开始时间
        String subStartTime = start_time.substring(5);
        String startTimeKey = getString(R.string.start_time_key_text);
        SpannableString startTimeSpannableString = new SpannableString(startTimeKey + subStartTime);
        startTimeSpannableString.setSpan(new AbsoluteSizeSpan(40), startTimeKey.length(), startTimeSpannableString.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        startTimeSpannableString.setSpan(new ForegroundColorSpan(getColor(R.color.value_main_text_color)), startTimeKey.length(), startTimeSpannableString.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        leaveStartTime.setText(startTimeSpannableString);
        //结束时间
        long totalTime_num = 0;
        long outTime = go_out_time * 60 * 60 * 1000;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault());
        try {
            Date parse = format.parse(start_time);
            long time = parse.getTime();
            long endTime = time + outTime;
            //totalTime
            totalTime_num = endTime - time;
            Log.d("TAG", "initData: starttime" + time);
            Log.d("TAG", "initData: outTime" + outTime);
            Log.d("TAG", "initData: endTime" + endTime);
            Log.d("TAG", "initData: totalTime_num" + totalTime_num);
            String endStr = format.format(endTime);
            String endTimeStr = endStr.substring(5);
            String endTimeKey = getString(R.string.end_time_key_text);
            SpannableString endTimeSpannableString = new SpannableString(endTimeKey + endTimeStr);
            endTimeSpannableString.setSpan(new AbsoluteSizeSpan(40), endTimeKey.length(), endTimeSpannableString.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
            endTimeSpannableString.setSpan(new ForegroundColorSpan(getColor(R.color.value_main_text_color)), endTimeKey.length(), endTimeSpannableString.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
            leaveEndTime.setText(endTimeSpannableString);
        } catch (ParseException e) {

        }
        //审批流程
        String stepKey = getString(R.string.leave_step_key);
        leaveStep.setText(Html.fromHtml(stepKey + "<font color='" + getColor(R.color.value_main_text_color) + "'>共1步 </font>" + "<font color='" + getColor(R.color.value_position_text_color) + "'>查看></font>"));
        //紧急联系人
        String contactKey = getString(R.string.contact_key_text);
        SpannableString contactSpannableString = new SpannableString(contactKey + contact_number);
        contactSpannableString.setSpan(new ForegroundColorSpan(getColor(R.color.value_main_text_color)), contactKey.length(), contactSpannableString.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        contactPerson.setText(contactSpannableString);
        //请假原因
        String leaveReasonKey = getString(R.string.leave_reason_key_text);
        SpannableString leaveReasonSpannableString = new SpannableString(leaveReasonKey + leave_reason);
        leaveReasonSpannableString.setSpan(new ForegroundColorSpan(getColor(R.color.value_main_text_color)), leaveReasonKey.length(), leaveReasonSpannableString.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        leaveReason.setText(leaveReasonSpannableString);
        //发起位置
        String positionKey = getString(R.string.position_key_text);
        SpannableString positionSpannableString = new SpannableString(positionKey + position);
        positionSpannableString.setSpan(new ForegroundColorSpan(getColor(R.color.value_position_text_color)), positionKey.length(), positionSpannableString.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        leavePosition.setText(positionSpannableString);
        //抄送人
        String ccManKey = getString(R.string.cc_man_key_text);
        SpannableString ccManSpannableString = new SpannableString(ccManKey + getString(R.string.cc_man_value_text));
        ccManSpannableString.setSpan(new ForegroundColorSpan(getColor(R.color.value_main_text_color)), ccManKey.length(), ccManSpannableString.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        CCMan.setText(ccManSpannableString);
        //申请人
        String userNameEnd = getString(R.string.user_name_end_text);
        userNameTv.setText((user_name + userNameEnd));
        //审批
        String teacherText = "一级：[" + identity + "]" + cc_man + " - 审批";
        SpannableString passSpannableString = new SpannableString(teacherText + getString(R.string.pass_text));
        passSpannableString.setSpan(new ForegroundColorSpan(getColor(R.color.step_round_green_color)), teacherText.length(), passSpannableString.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        teacherPassTv.setText(passSpannableString);
        //时间
        timeOfApplyTv.setText(subStartTime);
        timeOfPassTv.setText(subStartTime);
        int sunMinute = (int) (totalTime_num / 1000 / 60);
        int hour = sunMinute / 60;
        int minute = sunMinute % 60;
        StringBuffer buffer = new StringBuffer();
        if (hour > 0) {
            buffer.append(hour).append("小时");
        }
        if (minute != 0) {
            buffer.append(minute).append("分钟");
        }
        totalTime.setText(buffer);

    }

    private int getColor(int id) {
        return getContext().getResources().getColor(id);
    }

    private void initView(View view) {
        topTipTextTv = (TextView) view.findViewById(R.id.top_tip_text_tv);
        mLeaveType = (TextView) view.findViewById(R.id.leave_type);
        destroyRule = (TextView) view.findViewById(R.id.destroy_rule);
        leaveStartTime = (TextView) view.findViewById(R.id.leave_start_time);
        leaveEndTime = (TextView) view.findViewById(R.id.leave_end_time);
        leaveStep = (TextView) view.findViewById(R.id.leave_step);
        contactPerson = (TextView) view.findViewById(R.id.contact_person);
        leaveReason = (TextView) view.findViewById(R.id.leave_reason);
        leavePosition = (TextView) view.findViewById(R.id.leave_position);
        CCMan = (TextView) view.findViewById(R.id.CC_man);
        userNameTv = (TextView) view.findViewById(R.id.user_name_tv);
        timeOfApplyTv = (TextView) view.findViewById(R.id.time_of_apply_tv);
        teacherPassTv = (TextView) view.findViewById(R.id.teacher_pass_tv);
        timeOfPassTv = (TextView) view.findViewById(R.id.time_of_pass_tv);
        needLeave = (TextView) view.findViewById(R.id.need_leave);
        realTime = (TextView) view.findViewById(R.id.real_time);
        totalTime = (TextView) view.findViewById(R.id.total_time);
    }
}
