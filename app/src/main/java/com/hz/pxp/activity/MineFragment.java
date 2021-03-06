package com.hz.pxp.activity;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
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

public class MineFragment extends BaseFragment {

    private PasswordDAOImpl passwordDAO = DAOFactory.getInstance().getPasswordDAO();

    private ListView passwordList;
    private PasswordAdapter passwordAdapter;
    private ArrayList<PassItem> passItems = new ArrayList<>();

    private TextView noRecordHint;

    @Override
    protected void initViews(LayoutInflater inflater, ViewGroup container) {
        rootView = inflater.inflate(R.layout.fragment_mine, container, false);
        setHeaderTitle("览");
        noRecordHint = (TextView)rootView.findViewById(R.id.no_record_hint_txt);
        passwordList = (ListView)rootView.findViewById(R.id.list_mine);

    }

    @Override
    public void onResume() {
        super.onResume();
        freshView();
    }

    private class PasswordAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return passItems.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            final PassItem passItem = passItems.get(position);
            ViewHolder vh;
            if (convertView == null){
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_password,null);
                vh = new ViewHolder();
                vh.mName = (TextView)convertView.findViewById(R.id.p_name);
                vh.mUserName = (TextView)convertView.findViewById(R.id.p_user_name);
                vh.mUserNameLayout = (LinearLayout)convertView.findViewById(R.id.user_name_layout);
                vh.mPassword = (TextView)convertView.findViewById(R.id.p_pass_word);
                vh.mPasswordLayou = (LinearLayout)convertView.findViewById(R.id.pass_word_layout);
                vh.mEmailLayout = (LinearLayout)convertView.findViewById(R.id.p_email_layout);
                vh.mPhoneLayout = (LinearLayout)convertView.findViewById(R.id.p_phone_layout);
                vh.mEmail = (TextView)convertView.findViewById(R.id.p_email);
                vh.mPhone = (TextView)convertView.findViewById(R.id.p_phone);
                vh.mThirdNameLayout = (LinearLayout)convertView.findViewById(R.id.p_third_name_layout);
                vh.mThirdInfoLayout = (LinearLayout)convertView.findViewById(R.id.p_third_info_layout);
                vh.mThirdName = (TextView)convertView.findViewById(R.id.p_third_name);
                vh.mThirdInfo = (TextView)convertView.findViewById(R.id.p_third_info);
                convertView.setTag(vh);
            }else {
                vh =(ViewHolder) convertView.getTag();
            }

            if (passItem!=null){
                vh.mName.setText(passItem.name);
                if (TextUtils.isEmpty(CommonUtils.decryptionString(passItem.userName, PreferenceHelper.getString(Const.PM_USER_NAME)))){
                    vh.mUserNameLayout.setVisibility(View.GONE);
                }else {
                    vh.mUserName.setText(CommonUtils.decryptionString(passItem.userName, PreferenceHelper.getString(Const.PM_USER_NAME)));
                    vh.mUserNameLayout.setVisibility(View.VISIBLE);
                }
                if (TextUtils.isEmpty(CommonUtils.decryptionString(passItem.userName, PreferenceHelper.getString(Const.PM_USER_NAME)))){
                    vh.mUserNameLayout.setVisibility(View.GONE);
                }else {
                    vh.mUserName.setText(CommonUtils.decryptionString(passItem.userName, PreferenceHelper.getString(Const.PM_USER_NAME)));
                    vh.mUserNameLayout.setVisibility(View.VISIBLE);
                }
                if (TextUtils.isEmpty(CommonUtils.decryptionString(passItem.passWord, PreferenceHelper.getString(Const.PM_USER_NAME)))){
                    vh.mPasswordLayou.setVisibility(View.GONE);
                }else {
                    vh.mPassword.setText(CommonUtils.decryptionString(passItem.passWord, PreferenceHelper.getString(Const.PM_USER_NAME)));
                    vh.mPasswordLayou.setVisibility(View.VISIBLE);
                }
                if (TextUtils.isEmpty(CommonUtils.decryptionString(passItem.email, PreferenceHelper.getString(Const.PM_USER_NAME)))){
                    vh.mEmailLayout.setVisibility(View.GONE);
                }else {
                    vh.mEmail.setText(CommonUtils.decryptionString(passItem.email, PreferenceHelper.getString(Const.PM_USER_NAME)));
                    vh.mEmailLayout.setVisibility(View.VISIBLE);
                }
                if (TextUtils.isEmpty(CommonUtils.decryptionString(passItem.phone, PreferenceHelper.getString(Const.PM_USER_NAME)))){
                    vh.mPhoneLayout.setVisibility(View.GONE);
                }else {
                    vh.mPhone.setText(CommonUtils.decryptionString(passItem.phone, PreferenceHelper.getString(Const.PM_USER_NAME)));
                    vh.mPhoneLayout.setVisibility(View.VISIBLE);
                }
                if (passItem.isThird.equals("0")){
                    vh.mThirdNameLayout.setVisibility(View.GONE);
                    vh.mThirdInfoLayout.setVisibility(View.GONE);
                }else {
                    vh.mThirdNameLayout.setVisibility(View.VISIBLE);
                    vh.mThirdInfoLayout.setVisibility(View.VISIBLE);
                    vh.mThirdName.setText(passItem.thirdName);
                    vh.mThirdInfo.setText(passItem.thirdInfo);
                }
            }

            convertView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                    //    指定下拉列表的显示数据
                    final String[] selects = {"删除"};
                    //    设置一个下拉的列表选择项
                    builder.setItems(selects, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            switch (which) {
                                case 0:
                                    if (passwordDAO.delete(passItem)>0){
                                        Toast.makeText(getContext(),R.string.del_succeed,Toast.LENGTH_LONG).show();
                                        freshView();
                                    }
                                    break;
                            }
                        }
                    });
                    builder.show();
                    return false;
                }
            });

            return convertView;
        }
    }

    private class ViewHolder{
        TextView mName;
        TextView mUserName;
        LinearLayout mUserNameLayout;
        LinearLayout mPasswordLayou;
        TextView mPassword;
        LinearLayout mEmailLayout;
        TextView mEmail;
        LinearLayout mPhoneLayout;
        TextView mPhone;
        LinearLayout mThirdNameLayout;
        LinearLayout mThirdInfoLayout;
        TextView mThirdName;
        TextView mThirdInfo;
    }

    private void freshView(){
        passItems = passwordDAO.queryPassItems();

        if (passItems.size()>0){
            noRecordHint.setVisibility(View.GONE);
            passwordAdapter = new PasswordAdapter();
            passwordList.setAdapter(passwordAdapter);
        }else {
            noRecordHint.setVisibility(View.VISIBLE);
        }
    }

}
