package com.example.xxx.idcarddemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.baidu.apistore.sdk.ApiCallBack;
import com.baidu.apistore.sdk.ApiStoreSDK;
import com.baidu.apistore.sdk.network.Parameters;

import org.json.JSONException;
import org.json.JSONObject;

public class MainsActivity extends AppCompatActivity {

    private EditText et_idcard;
    private Button btn_ok;
    private TextView tv_sex;//性别M-男，F-女，N-未知
    private TextView tv_birthday;//出生日期
    private TextView tv_address;//地址

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mains);
        initView();
    }

    private void initView() {
        et_idcard = (EditText) findViewById(R.id.et_idcard);
        btn_ok = (Button) findViewById(R.id.btn_ok);
        tv_sex = (TextView) findViewById(R.id.tv_sex);
        tv_birthday = (TextView) findViewById(R.id.tv_birthday);
        tv_address = (TextView) findViewById(R.id.tv_address);

        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                apiTest();
            }
        });
    }

    private void apiTest() {

        Parameters para = new Parameters();

        para.put("id", et_idcard.getText().toString().trim());
        ApiStoreSDK.execute("http://apis.baidu.com/apistore/idservice/id",
                ApiStoreSDK.GET,
                para,
                new ApiCallBack() {

                    @Override
                    public void onSuccess(int status, String responseString) {
                        Log.i("sdkdemo", "onSuccess");
                        try {
                            JSONObject jsonObject = new JSONObject(responseString);
                            JSONObject result = jsonObject.getJSONObject("retData");
                            tv_address.setText(result.getString("address"));
                            String sex = result.getString("sex");
                            if ("M".equals(sex)) {
                                tv_sex.setText("男");
                            } else if ("F".equals(sex)) {
                                tv_sex.setText("女");
                            } else {
                                tv_sex.setText("人妖");
                            }
                            tv_birthday.setText(result.getString("birthday"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onComplete() {
                        Log.i("sdkdemo", "onComplete");
                    }

                    @Override
                    public void onError(int status, String responseString, Exception e) {
                        Log.i("sdkdemo", "onError, status: " + status);
                        Log.i("sdkdemo", "errMsg: " + (e == null ? "" : e.getMessage()));
                        tv_sex.setText(getStackTrace(e));
                    }
                });
    }

    String getStackTrace(Throwable e) {
        if (e == null) {
            return "";
        }
        StringBuilder str = new StringBuilder();
        str.append(e.getMessage()).append("\n");
        for (int i = 0; i < e.getStackTrace().length; i++) {
            str.append(e.getStackTrace()[i]).append("\n");
        }
        return str.toString();
    }
}