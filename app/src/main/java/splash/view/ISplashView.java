package splash.view;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

public interface ISplashView {

    void showToast(String msg);
    void hideBottomUIMenu();

    void showPics(List<Integer> picList);

    void startNewPage(Class newPage);

    void finishNow();

    void skip2Login();

    void skip2Main();

    public void setSkipText(String str);

    public void cancelTask();

    public void countDownTask();

    public void startTask();
    public String getUserData();
}
