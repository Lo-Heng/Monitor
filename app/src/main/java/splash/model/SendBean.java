package splash.model;

import android.util.Log;
import org.json.JSONException;
import org.json.JSONObject;
import pub_util.MyHttpUtil;

public class SendBean {
    private final String server = "http://116.6.120.56:9090";//服务器地址，不可修改
    private final String reg_url = "/api/user/regist";//注册接口，不可修改
    private final String login_url = "/api/user/login";//登录接口，不可修改
    private String user_id;
    private String user_name;//
    private String args;//json参数
    private String token;//登陆后产生的校验码


    String msg = "";
    JSONObject argJSON, resultJASON;

    public SendBean() {
    }

    public SendBean(JSONObject argJSON) {
        this.argJSON = argJSON;
    }

    /**
     * 发送注册请求
     * @return
     */
    public String requestRegist() {
        String parms = "user_name=" + user_name + "&user_id=" + user_id + "&args=" + argJSON + "&token=" + token;
        Log.d("SplashSend",parms);
        String result = MyHttpUtil.sendGet(server + reg_url, parms);
        return result;
    }
    /**
     * 发送登录请求
     * @return
     */
    public String requestLogin() {
        String parms = "user_name=" + user_name + "&user_id=" + user_id + "&args=" + argJSON + "&token=" + token;
        Log.d("SplashSend",parms);
        String result = MyHttpUtil.sendGet(server + login_url, parms);
        return result;
    }
    public JSONObject getResultJASON() {
        return resultJASON;
    }



    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getArgs() {
        return args;
    }

    public void setArgs(String args) {
        this.args = args;
    }

    public String getToken() {
        return token;
    }

    public String getReg_url() {
        return reg_url;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public JSONObject getArgJSON() {
        return argJSON;
    }

    public void setArgJSON(JSONObject argJSON) {
        this.argJSON = argJSON;
    }
}
