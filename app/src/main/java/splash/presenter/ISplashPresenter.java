package splash.presenter;

import android.app.Activity;
import android.content.Context;

import java.util.List;

public interface ISplashPresenter {

    void fetch();
    void doLogin(final Context context);
    void startMain(final Context context);
}


