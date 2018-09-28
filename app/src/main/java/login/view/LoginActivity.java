package login.view;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.*;

import com.busterlo.monitor.R;

import java.lang.reflect.Field;


public class LoginActivity extends AppCompatActivity implements ILoginActivity, View.OnClickListener {
    private String userName, userPwd;
    private Button mRegBtn;
    private Button mLoginBtn;
    private EditText mAccountEt, mPasswordEt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();

        //绑定控件
        mRegBtn = findViewById(R.id.reg_btn);
        mLoginBtn = findViewById(R.id.login_btn);
        mAccountEt = findViewById(R.id.et_account);
        mPasswordEt = findViewById(R.id.et_password);

        mRegBtn.setOnClickListener(this);
        mLoginBtn.setOnClickListener(this);
        showToast("login");
    }


    @Override
    public String getUserName() {
        return userName;
    }

    @Override
    public String getPassword() {
        return userPwd;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login_btn:
                userName = String.valueOf(mAccountEt.getText());
                userPwd = String.valueOf(mPasswordEt.getText());

                //登录dologin
                if (!userPwd.isEmpty() && !userName.isEmpty()) {
                    showToast("userName" + userName + "userpwd" + userPwd);
                    Log.d("LoginActivity", "username:" + userName + "userpwd:" + userPwd);
                }
                break;
            case R.id.reg_btn:

                break;
            default:
                break;
        }
    }

    /*
    显示消息
 */
    @Override
    public void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }
}
