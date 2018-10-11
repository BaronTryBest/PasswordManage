package com.hz.pxp.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.hz.pxp.R;
import com.hz.pxp.common.Const;
import com.hz.pxp.common.PreferenceHelper;
import com.hz.pxp.database.dao.DAOFactory;

public class IndexActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText mUserName;
    private TextView mLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);// 隐藏标题
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);// 设置全屏
        setContentView(R.layout.activity_index);

        PreferenceHelper.init(this);
        DAOFactory.init(this);

        mUserName = (EditText)findViewById(R.id.edtTxt_login_name);
        mLogin = (TextView)findViewById(R.id.login_text);
        mLogin.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        if (view == mLogin){
            if (TextUtils.isEmpty(mUserName.getText().toString())){
                Toast.makeText(IndexActivity.this,"请输入用户名",Toast.LENGTH_LONG).show();
            }else {
                PreferenceHelper.putString(Const.PM_USER_NAME,mUserName.getText().toString());
                startActivity(new Intent(IndexActivity.this,MainTabActivity.class));
            }
        }
    }
}
