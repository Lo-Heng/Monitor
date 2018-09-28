package splash.model;


import android.os.Handler;

import com.busterlo.monitor.R;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.LogRecord;

import splash.presenter.ISplashPresenter;

public class SplashModel implements ISplashModel {

    public SplashModel() {


    }


    @Override
    public void getPics(ISplashListener iSplashListener) {
        List<Integer> sPics = new ArrayList<>();
        sPics.add(R.drawable.tosot1);
        sPics.add(R.drawable.tosot2);
        sPics.add(R.drawable.tosot3);
        iSplashListener.onComplete(sPics);
    }
}
