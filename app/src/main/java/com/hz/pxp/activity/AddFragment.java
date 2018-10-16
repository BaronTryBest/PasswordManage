package com.hz.pxp.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.hz.pxp.R;
import com.hz.pxp.common.Const;
import com.hz.pxp.common.PreferenceHelper;
import com.hz.pxp.database.dao.DAOFactory;
import com.hz.pxp.database.dao.impl.PasswordDAOImpl;
import com.hz.pxp.model.PassItem;

public class AddFragment extends BaseFragment {

    private PasswordDAOImpl passwordDAO = DAOFactory.getInstance().getPasswordDAO();
    private PassItem mPassItem;
    private Button mAddButton;

    private EditText mName,mUserName,mPassWord,mEmail,mPhone,mThirdLoginName,mThirdLoginInfo;
    private CheckBox mIsThird;
    private LinearLayout mThirdLayout;

    @Override
    protected void initViews(LayoutInflater inflater, ViewGroup container) {
        rootView = inflater.inflate(R.layout.fragment_add, container, false);

        mName = (EditText)rootView.findViewById(R.id.name);
        mUserName = (EditText)rootView.findViewById(R.id.user_name);
        mPassWord = (EditText)rootView.findViewById(R.id.password);
        mEmail = (EditText)rootView.findViewById(R.id.email);
        mPhone = (EditText)rootView.findViewById(R.id.phone);
        mIsThird = (CheckBox)rootView.findViewById(R.id.third_login);
        mThirdLayout = (LinearLayout)rootView.findViewById(R.id.third_login_info_layout);
        mThirdLoginName = (EditText) rootView.findViewById(R.id.third_name);
        mThirdLoginInfo = (EditText)rootView.findViewById(R.id.third_info);

        mIsThird.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    mThirdLayout.setVisibility(View.VISIBLE);
                }else {
                    mThirdLayout.setVisibility(View.GONE);
                }
            }
        });

        mAddButton = (Button) rootView.findViewById(R.id.add_pw);
        mAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (TextUtils.isEmpty(mName.getText().toString())){
                    Toast.makeText(getContext(),"请输入关键字",Toast.LENGTH_LONG).show();
                }else {
                    if (passwordDAO.isKeyNameExist(mName.getText().toString())){
                        //关键字已经存在
                        final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                        builder.setTitle("关键字已存在");
                        builder.setMessage("是否要覆盖之前的记录？");
                        builder.setPositiveButton("覆盖", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                mPassItem = new PassItem();
                                mPassItem.name = mName.getText().toString();
                                mPassItem.userName = mUserName.getText().toString();
                                mPassItem.passWord = mPassWord.getText().toString();
                                mPassItem.passType = "nomal";
                                mPassItem.email = mEmail.getText().toString();
                                mPassItem.phone = mPhone.getText().toString();
                                mPassItem.isThird = mIsThird.isChecked()?"1":"0";
                                if (mIsThird.isChecked()){
                                    mPassItem.thirdName = mThirdLoginName.getText().toString();
                                    mPassItem.thirdInfo = mThirdLoginInfo.getText().toString();
                                }else {
                                    mPassItem.thirdName = "";
                                    mPassItem.thirdInfo = "";
                                }
                                mPassItem.owner = PreferenceHelper.getString(Const.PM_USER_NAME);
                                passwordDAO.save(mPassItem);
                            }
                        });
                        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                            }
                        });
                        builder.show();
                    }else {
                        mPassItem = new PassItem();
                        mPassItem.name = mName.getText().toString();
                        mPassItem.userName = mUserName.getText().toString();
                        mPassItem.passWord = mPassWord.getText().toString();
                        mPassItem.passType = "nomal";
                        mPassItem.email = mEmail.getText().toString();
                        mPassItem.phone = mPhone.getText().toString();
                        mPassItem.isThird = mIsThird.isChecked()?"1":"0";
                        if (mIsThird.isChecked()){
                            mPassItem.thirdName = mThirdLoginName.getText().toString();
                            mPassItem.thirdInfo = mThirdLoginInfo.getText().toString();
                        }else {
                            mPassItem.thirdName = "";
                            mPassItem.thirdInfo = "";
                        }
                        mPassItem.owner = PreferenceHelper.getString(Const.PM_USER_NAME);
                        passwordDAO.save(mPassItem);
                    }
                }
            }
        });
    }
}
