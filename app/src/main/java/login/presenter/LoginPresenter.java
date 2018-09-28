package login.presenter;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import org.json.JSONException;
import org.json.JSONObject;
import pub_server.ReturnLogin;
import pub_server.UserInfo;
import splash.model.SendBean;

public class LoginPresenter {







    SendBean mSendBean;
    ReturnLogin mReturnLogin;
    public void doLogin(final Context context) {

        String user_id = "0";
        String user_name = "";
        String args = "";

        mSendBean = new SendBean();

        /**
         * 包装成方法 创建jason
         */
        JSONObject jsonArgs = new JSONObject();
        try {
            jsonArgs.put("user_name", "lo");
            jsonArgs.put("password", "123");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        //1 参数配置
        mSendBean.setArgJSON(jsonArgs);
        mSendBean.setUser_id(user_id);
        mSendBean.setUser_name(user_name);
        Thread mThread = new Thread(new Runnable() {
            @Override
            public void run() {
                //发出登录请求
                String result = mSendBean.requestLogin();

                Log.d("SplashPresenter", "result" + result);
                //解析登录JSON
                getLoginJSON(result);
                //保存token
                saveUserData(context);
                Log.d("SplashPresenter", String.valueOf("token:" + mReturnLogin.getUserInfo().getToken()));
            }
        });
        mThread.start();
    }

    public void saveUserData(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("UserData", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();//获取编辑器
        editor.putString("token", mReturnLogin.getUserInfo().getToken());
        editor.commit();//提交修改
    }
    /**
     * 将json解析
     *
     * @param json 传入字符串
     * @return
     */
    public void getLoginJSON(String json) {
        try {
            JSONObject resultJSON = new JSONObject(json);
            mReturnLogin.setSuccess(resultJSON.getBoolean("success"));
            mReturnLogin.setCode(resultJSON.getString("code"));
            mReturnLogin.setMsg(resultJSON.getString("msg"));
            JSONObject temp = new JSONObject(resultJSON.getString("userInfo"));
            UserInfo mUserInfo = new UserInfo();
            mUserInfo.setNick_name(temp.getString("nick_name"));
            mUserInfo.setToken(temp.getString("token"));
            mUserInfo.setUser_id(temp.getInt("user_id"));
            mReturnLogin.setUserInfo(mUserInfo);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}