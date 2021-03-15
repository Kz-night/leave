package com.example.askforleave.fragments;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.askforleave.R;
import com.example.askforleave.base.BaseFragment;
import com.king.zxing.util.CodeUtils;

/**
 * Created by AK on 2021/3/2 21:56.
 * God bless my code!
 */
public class QRCodeFragment extends BaseFragment {

    private TextView leaveStateTv;
    private ImageView qrCodeIv;
    private TextView topTipTextTv;

    @Override
    protected View onSubViewLoad(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.rq_code_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
        initData();
    }

    private void initData() {
        topTipTextTv.setText("请出示专属核验二维码，用于验证请假单真伪");
        Bitmap qrCode = CodeUtils.createQRCode("1450232082@qq.com", 200);
        qrCodeIv.setImageBitmap(qrCode);
    }

    private void initView(View view) {
        leaveStateTv = (TextView) view.findViewById(R.id.leave_state_tv);
        qrCodeIv = (ImageView) view.findViewById(R.id.rq_code_iv);
        topTipTextTv = (TextView) view.findViewById(R.id.top_tip_text_tv);
    }
}
