package com.kxiang.quick.function.activity.erwm;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.WriterException;
import com.google.zxing.activity.CaptureActivity;
import com.google.zxing.encoding.EncodingHandler;
import com.kexiang.function.utils.CameraUtils;
import com.kxiang.quick.R;
import com.kxiang.quick.base.BaseActivity;

public class QrCodeActivity extends BaseActivity implements View.OnClickListener {

    private Button openQrCodeScan;
    private TextView qrCodeText;
    private EditText text;
    private Button CreateQrCode;
    private ImageView QrCode;


    //打开扫描界面请求码
    private int REQUEST_CODE = 0x01;
    //扫描成功返回码
    private int RESULT_OK = 0xA1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qr_code);

    }

    @Override
    protected void initView() {

        initTitle();
        openQrCodeScan = (Button) findViewById(R.id.openQrCodeScan);
        openQrCodeScan.setOnClickListener(this);
        qrCodeText = (TextView) findViewById(R.id.qrCodeText);
        text = (EditText) findViewById(R.id.text);
        CreateQrCode = (Button) findViewById(R.id.CreateQrCode);
        CreateQrCode.setOnClickListener(this);
        QrCode = (ImageView) findViewById(R.id.QrCode);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.openQrCodeScan:


                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

                    if (ContextCompat.checkSelfPermission(this,
                            Manifest.permission.CAMERA)
                            != PackageManager.PERMISSION_GRANTED) {

                        /**
                         * 请求权限是一个异步任务  不是立即请求就能得到结果 在结果回调中返回
                         */
                        requestPermissions(new String[]{Manifest.permission.CAMERA}, 2);
                    }

                }

                //打开二维码扫描界面
                if (CameraUtils.isCameraCanUse()) {
                    Intent intent = new Intent(thisActivity, CaptureActivity.class);
                    startActivityForResult(intent, REQUEST_CODE);
                }
                else {
                    Toast.makeText(this, "请打开此应用的摄像头权限！", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.CreateQrCode:
                try {
                    //获取输入的文本信息
                    String str = text.getText().toString().trim();
                    if (str != null && !"".equals(str.trim())) {
                        //根据输入的文本生成对应的二维码并且显示出来
                        Bitmap mBitmap = EncodingHandler.createQRCode(text.getText().toString(), 500);
                        if (mBitmap != null) {
                            Toast.makeText(this, "二维码生成成功！", Toast.LENGTH_SHORT).show();
                            QrCode.setImageBitmap(mBitmap);
                        }
                    }
                    else {
                        Toast.makeText(this, "文本信息不能为空！", Toast.LENGTH_SHORT).show();
                    }
                } catch (WriterException e) {
                    e.printStackTrace();
                }
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //扫描结果回调
        if (resultCode == RESULT_OK) { //RESULT_OK = -1
            Bundle bundle = data.getExtras();
            String scanResult = bundle.getString("qr_scan_result");
            //将扫描出的信息显示出来
            qrCodeText.setText(scanResult);
        }
    }
}
