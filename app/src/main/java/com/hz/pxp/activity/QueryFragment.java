package com.hz.pxp.activity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.hz.pxp.R;
import com.hz.pxp.common.CommonUtils;
import com.hz.pxp.common.Const;
import com.hz.pxp.common.PreferenceHelper;
import com.hz.pxp.database.dao.DAOFactory;
import com.hz.pxp.database.dao.impl.PasswordDAOImpl;
import com.hz.pxp.model.PassItem;

import java.util.ArrayList;

import static android.content.Context.CLIPBOARD_SERVICE;

public class QueryFragment extends BaseFragment implements View.OnClickListener{

    private PasswordDAOImpl passwordDAO = DAOFactory.getInstance().getPasswordDAO();
    private EditText mQueryEditText;
    private ImageView mQueryImg;
    private PassItem mQueryItem;

    private ClipboardManager myClipboard;
    private ClipData myClip;

    private LinearLayout nameLayout,userNameLayout,passwordLayout,emailLayout,phoneLayout,thirdNameLayout,thirdInfoLayout;
    private TextView nameTxt,userNameTxt,passwordTxt,emailTxt,phoneTxt,thirdNameTxt,thirdInfoTxt;
    private TextView hintTxt;
    private ArrayList<PassItem> mQueryList;

    @Override
    protected void initViews(LayoutInflater inflater, ViewGroup container) {
        rootView = inflater.inflate(R.layout.fragment_check, container, false);

        setHeaderTitle("查");
        nameLayout = (LinearLayout)rootView.findViewById(R.id.name_layout);
        userNameLayout = (LinearLayout)rootView.findViewById(R.id.user_name_layout);
        passwordLayout = (LinearLayout)rootView.findViewById(R.id.pass_word_layout);
        emailLayout = (LinearLayout)rootView.findViewById(R.id.email_layout);
        phoneLayout = (LinearLayout)rootView.findViewById(R.id.phone_layout);
        thirdNameLayout = (LinearLayout)rootView.findViewById(R.id.third_name_layout);
        thirdInfoLayout = (LinearLayout)rootView.findViewById(R.id.third_info_layout);

        nameTxt = (TextView)rootView.findViewById(R.id.name_query);
        userNameTxt = (TextView)rootView.findViewById(R.id.user_name_query);
        passwordTxt = (TextView)rootView.findViewById(R.id.pass_word_query);
        emailTxt = (TextView)rootView.findViewById(R.id.email_query);
        phoneTxt = (TextView)rootView.findViewById(R.id.phone_query);
        thirdNameTxt = (TextView)rootView.findViewById(R.id.third_name_query);
        thirdInfoTxt = (TextView)rootView.findViewById(R.id.third_info_query);
        hintTxt = (TextView)rootView.findViewById(R.id.hint_query);

        nameTxt.setOnClickListener(this);
        userNameTxt.setOnClickListener(this);
        passwordTxt.setOnClickListener(this);
        emailTxt.setOnClickListener(this);
        phoneTxt.setOnClickListener(this);
        thirdNameTxt.setOnClickListener(this);
        thirdInfoTxt.setOnClickListener(this);

        mQueryEditText = (EditText)rootView.findViewById(R.id.edit_query);
        mQueryImg = (ImageView)rootView.findViewById(R.id.img_query);
        mQueryImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(getActivity().getWindow().getDecorView().getWindowToken(), 0);

                if (TextUtils.isEmpty(mQueryEditText.getText().toString())){
                    Toast.makeText(getContext(),"请输入关键字",Toast.LENGTH_SHORT).show();
                    freshResult(null);
                    hintTxt.setVisibility(View.INVISIBLE);
                }else {
                    mQueryList = passwordDAO.queryIsKeyNameExist(mQueryEditText.getText().toString());

                    if (mQueryList.size() == 0){
                        freshResult(null);
                        hintTxt.setVisibility(View.VISIBLE);
                    }else if (mQueryList.size() == 1){
                        hintTxt.setVisibility(View.INVISIBLE);
                        mQueryItem = passwordDAO.queryName(mQueryList.get(0).name);
                        freshResult(mQueryItem);
                    }else {
                        final String[] selects = new String[mQueryList.size()];
                        for (int i = 0 ; i < mQueryList.size() ; i ++ ){
                            PassItem passItem = mQueryList.get(i);
                            selects[i] = passItem.name;
                        }

                        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                        builder.setItems(selects, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                hintTxt.setVisibility(View.INVISIBLE);
                                mQueryItem = passwordDAO.queryName(selects[which]);
                                freshResult(mQueryItem);
                            }
                        });
                        builder.show();
                    }

//                    if (passwordDAO.isKeyNameExist(mQueryEditText.getText().toString()).size()>0){
//                        hintTxt.setVisibility(View.INVISIBLE);
//                        mQueryItem = passwordDAO.queryName(mQueryEditText.getText().toString());
//                        freshResult(mQueryItem);
//                    }else {
//                        freshResult(null);
//                        hintTxt.setVisibility(View.VISIBLE);
//                    }
                }
            }
        });


        myClipboard = (ClipboardManager)getActivity().getSystemService(CLIPBOARD_SERVICE);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    public void freshResult(PassItem passItem){
        if (passItem == null){
            nameTxt.setText("");
            userNameTxt.setText("");
            passwordTxt.setText("");
            emailTxt.setText("");
            phoneTxt.setText("");
            thirdInfoLayout.setVisibility(View.GONE);
            thirdNameLayout.setVisibility(View.GONE);
        }else {
            nameTxt.setText(passItem.name);
            userNameTxt.setText(CommonUtils.decryptionString(passItem.userName, PreferenceHelper.getString(Const.PM_USER_NAME)));
            passwordTxt.setText(CommonUtils.decryptionString(passItem.passWord, PreferenceHelper.getString(Const.PM_USER_NAME)));
            emailTxt.setText(CommonUtils.decryptionString(passItem.email, PreferenceHelper.getString(Const.PM_USER_NAME)));
            phoneTxt.setText(CommonUtils.decryptionString(passItem.phone, PreferenceHelper.getString(Const.PM_USER_NAME)));
            if (passItem.isThird.equals("0")){
                thirdInfoLayout.setVisibility(View.GONE);
                thirdNameLayout.setVisibility(View.GONE);
            }else {
                thirdInfoLayout.setVisibility(View.VISIBLE);
                thirdInfoTxt.setText(passItem.thirdInfo);
                thirdNameLayout.setVisibility(View.VISIBLE);
                thirdNameTxt.setText(passItem.thirdName);
            }
        }
    }

    @Override
    public void onClick(View v) {
        if (v == nameTxt){
            myClip = ClipData.newPlainText("text", nameTxt.getText().toString());
        }else if (v == userNameTxt){
            myClip = ClipData.newPlainText("text", userNameTxt.getText().toString());
        }else if (v == passwordTxt){
            myClip = ClipData.newPlainText("text", passwordTxt.getText().toString());
        }else if (v == emailTxt){
            myClip = ClipData.newPlainText("text", emailTxt.getText().toString());
        }else if (v == phoneTxt){
            myClip = ClipData.newPlainText("text", phoneTxt.getText().toString());
        }else if (v == thirdNameTxt){
            myClip = ClipData.newPlainText("text", thirdNameTxt.getText().toString());
        }else if (v == thirdInfoTxt){
            myClip = ClipData.newPlainText("text", thirdInfoTxt.getText().toString());
        }

        myClipboard.setPrimaryClip(myClip);
    }
}
