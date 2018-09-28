package splash.presenter;


import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;


import java.lang.ref.WeakReference;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import android.view.View;
import android.widget.Button;
import login.view.LoginActivity;
import org.json.JSONException;
import org.json.JSONObject;
import pub_server.ReturnLogin;
import pub_server.UserInfo;
import splash.model.ISplashModel;
import splash.model.SplashModel;
import splash.view.ISplashView;

import splash.model.SendBean;


public class SplashPresenter<T extends ISplashView> implements ISplashPresenter {

    private static String result = "";
    private String access;
    private String code;
    private Timer mTimer;
    // View弱引用
    protected WeakReference<T> mSplashViewRef;

    //model引用
    ISplashModel splashmodel = new SplashModel();
    private SendBean mSendBean;
    private ReturnLogin mReturnLogin = new ReturnLogin();
    private static int sec = 5;
    //    private ISplashView mSplashViewRef ;
    //构造方法
    public SplashPresenter(T view) {
        sec =5;
        this.mSplashViewRef = new WeakReference<T>(view);
    }

    @Override
    public void startMain(final Context context) {

        //打开定时器
        mSplashViewRef.get().startTask();



    }

    /**
     * 登录
     */
    @Override
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
                result = mSendBean.requestLogin();

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


    /**
     * 干两件事情
     * 第一：获取一下token
     * 第二：倒计时更新一下显示
     */
    public void updateSecond(){
        String token = mSplashViewRef.get().getUserData();
        Log.d("SplashPresenter", "token in startMain" + token);
        if (token.isEmpty()) {
            //跳转到登录页
            mSplashViewRef.get().skip2Login();
//            mSplashViewRef.get().startNewPage(LoginActivity.class);

        } else {
            //跳转到主页
            mSplashViewRef.get().skip2Main();
        }

        mSplashViewRef.get().setSkipText("点击跳过 "+ sec);
        if(sec < 0){
            mSplashViewRef.get().cancelTask();
        }
        else {
            sec--;
        }

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

    @Override
    public void fetch() {
        if (mSplashViewRef.get() != null) {
            splashmodel.getPics(new ISplashModel.ISplashListener() {
                @Override
                public void onComplete(List<Integer> pics) {
                    //返回数据，显示
                    mSplashViewRef.get().showPics(pics);
                }
            });
        }
    }


}
