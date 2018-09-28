package pub_util;


import android.util.Log;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class MyHttpUtil implements IMyHttpUtil {

    /**
     * @param url
     * @param params 参数 a=xx&b=xx&c=xx的形式
     * @return result 返回结果以字符串的形式
     */

    public  static String sendGet(String url, String params) {
        String result = "";
        BufferedReader in = null;
        try {

            String urlName = url + "?" + params;
            URL realUrl = new URL(urlName);
            //打开连接
            URLConnection conn = realUrl.openConnection();
            //实际连接
            conn.connect();
            //定义BufferedReader输入流来读取来读取URL的响应
            in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }

        } catch (Exception e) {
            Log.d("MyHttpUtil", "发送GET请求异常" + e);
            e.printStackTrace();
        } finally {
            try {
                if (in != null) {
                    //关闭输入流
                    in.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        Log.d("MyHttpUtil", "result" + result);
        return result;
    }
    /**
     * 将json解析
     *
     * @param result 传入字符串
     * @return
     */
    public void analyzeJSON(String result, JSONObject resultJASON) {
        try {
            resultJASON = new JSONObject(result);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
