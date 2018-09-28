package splash.model;

import java.util.List;

public interface ISplashModel {
    void getPics(ISplashListener iSplashListener);
    interface ISplashListener {
        void onComplete(List<Integer> pics);
    }
}
