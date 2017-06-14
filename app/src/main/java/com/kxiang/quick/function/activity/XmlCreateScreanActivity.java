package com.kxiang.quick.function.activity;

import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.kexiang.function.view.own.star.StarLikesView;
import com.kxiang.quick.R;
import com.kxiang.quick.base.BaseActivity;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;

public class XmlCreateScreanActivity extends BaseActivity implements View.OnClickListener {

    /**
     * 请输入xDP值
     */
    private EditText et_xdp;
    /**
     * 请输入yDP值
     */
    private EditText et_ydp;
    /**
     * 生成
     */
    private Button btn_sure;
    /**
     * 720
     */
    private EditText et_x;
    /**
     * 1280
     */
    private EditText et_y;
    /**
     * 星星加
     */
    private Button btn_add;
    /**
     * 星星减
     */
    private Button btn_sub;
    private StarLikesView star_normal;
    private StarLikesView star_scale;
    private StarLikesView star_rotate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xml_create_screan);
        initView();
        initStatusBarColor();
//
//        String URL_BASE = "http://c.3g.163.com/";
//        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl(URL_BASE)
//                .build();
//        ApiNetworkAddressService apiService = retrofit.create(ApiNetworkAddressService.class);
//        apiService
//                .getNewsListTest("headline", "T1348647909107", 0)
//                .enqueue(new Callback<ResponseBody>() {
//                    @Override
//                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
//                        try {
//                            LogUtils.toE("response","response:"+response.body().string());
//                        } catch (IOException e) {
//                            e.printStackTrace();
//                        }
//                    }
//
//                    @Override
//                    public void onFailure(Call<ResponseBody> call, Throwable t) {
//
//                    }
//                });
    }

    @Override
    protected void initView() {

        et_xdp = (EditText) findViewById(R.id.et_xdp);
        et_ydp = (EditText) findViewById(R.id.et_ydp);
        btn_sure = (Button) findViewById(R.id.btn_sure);
        btn_sure.setOnClickListener(this);
        et_x = (EditText) findViewById(R.id.et_x);
        et_y = (EditText) findViewById(R.id.et_y);
        btn_add = (Button) findViewById(R.id.btn_add);
        btn_add.setOnClickListener(this);
        btn_sub = (Button) findViewById(R.id.btn_sub);
        btn_sub.setOnClickListener(this);
        star_normal = (StarLikesView) findViewById(R.id.star_normal);
        star_scale = (StarLikesView) findViewById(R.id.star_scale);
        star_rotate = (StarLikesView) findViewById(R.id.star_rotate);
        star_scale.setStytle(StarLikesView.SCALE);
        star_rotate.setStytle(StarLikesView.ROTATE);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_sure:
                createXml("x", Float.parseFloat(et_xdp.getText().toString()),
                        Float.parseFloat(et_x.getText().toString()), "lay_x.xml");
                createXml("y", Float.parseFloat(et_ydp.getText().toString()),
                        Float.parseFloat(et_y.getText().toString()), "lay_y.xml");
                break;
            case R.id.btn_add:
                star_normal.addStar();
                star_scale.addStar();
                star_rotate.addStar();
                break;
            case R.id.btn_sub:
                star_normal.subStar();
                star_scale.subStar();
                star_rotate.subStar();
                break;
        }
    }

    public static float change(float a) {
        int temp = (int) (a * 100);
        return temp / 100f;
    }

    private void createXml(String type, float createSize, float baseSize, String fileName) {
//        　StringBuilder：线程非安全的
//        　　　　StringBuffer：线程安全的
        StringBuffer buffer = new StringBuffer();

        buffer.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>");
        buffer.append("\n");
        buffer.append("<resources>");
        buffer.append("\n");

        float single = createSize / baseSize;
        for (int i = 1; i <= baseSize; i++) {
            buffer.append("<dimen name=\"");
            buffer.append(type);
            buffer.append(i);
            buffer.append("\">");
            buffer.append(change(single * i));
            buffer.append("dp");
            buffer.append("</dimen>");
            buffer.append("\n");
        }
        buffer.append("</resources>");
        writeFile(buffer.toString(), fileName);
    }

    private void writeFile(String content, String fileName) {
        try {


            File fileDirs = new File(
                    Environment.getExternalStorageDirectory().getAbsolutePath() +
                            "/layoutResources/");

            // if file doesnt exists, then create it
            if (!fileDirs.exists()) {
                fileDirs.mkdirs();
            }
            File file = new File(fileDirs, fileName);

            if (!file.exists()) {
                file.createNewFile();
            }
            else {
                file.delete();
                file.createNewFile();
            }


//            FileOutputStream fos = new FileOutputStream(file);
//            // 写入内容
//            fos.write(content.getBytes());
//            fos.flush();
//            fos.close();
            FileWriter fw = new FileWriter(file.getAbsoluteFile());
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(content);
            bw.close();


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean writeSDcard(String path, String name, String content)
            throws IOException {
        boolean tempBoolean = false;
        // 创建文件夹
        File mkPath = new File(path);
        mkPath.mkdir();
        // 创建文件
        File fileName = new File(mkPath, name);
        fileName.createNewFile();
        FileOutputStream fos = new FileOutputStream(fileName);
        // 写入内容
        fos.write(content.getBytes());
        fos.flush();
        fos.close();

        tempBoolean = true;
        return tempBoolean;

    }
}
